package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.ai.PlaceholderAiRepository
import com.example.data.local.AppDatabase
import com.example.data.local.ConversationLogEntity
import com.example.data.local.DayMilestoneEntity
import com.example.data.local.LanguageMemoryEntity
import com.example.data.local.NpcRelationshipEntity
import com.example.data.local.WorldStateEntity
import com.example.data.repository.GameRepository
import com.example.data.story.NewHavenWorldCatalog
import com.example.domain.model.DialogueChoice
import com.example.domain.model.LifeCapacities
import com.example.domain.model.LocationId
import com.example.domain.model.SceneNode
import com.example.domain.model.SupportLevel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SceneUiState(
    val scene: SceneNode? = null,
    val supportLevel: SupportLevel = SupportLevel.DISCOVER,
    val observeCuesEnabled: Boolean = false,
    val isSendingAi: Boolean = false,
    val aiError: String? = null,
    val choiceResolved: Boolean = false,
    val pendingNextSceneId: String? = null,
    val readyForDaySummary: Boolean = false,
    val latestOutcomeNote: String? = null
)

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GameRepository(
        dao = AppDatabase.getInstance(application).gameDao(),
        aiRepository = PlaceholderAiRepository()
    )

    val isPlaceholderAiMode: Boolean = repository.isPlaceholderAiMode

    val worldState: StateFlow<WorldStateEntity> = repository.worldStateFlow
        .map { it ?: WorldStateEntity() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), WorldStateEntity())

    val npcRelationships: StateFlow<List<NpcRelationshipEntity>> = repository.npcRelationshipsFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val languageMemories: StateFlow<List<LanguageMemoryEntity>> = repository.languageMemoriesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val milestones: StateFlow<List<DayMilestoneEntity>> = repository.milestonesFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _sceneUiState = MutableStateFlow(SceneUiState())
    val sceneUiState: StateFlow<SceneUiState> = _sceneUiState.asStateFlow()

    val sceneConversation: StateFlow<List<ConversationLogEntity>> = _sceneUiState
        .flatMapLatest { ui ->
            val sceneId = ui.scene?.id
            if (sceneId.isNullOrBlank()) flowOf(emptyList())
            else repository.observeSceneConversation(sceneId)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            repository.ensureInitialized()
        }
    }

    fun lifeCapacitiesFrom(world: WorldStateEntity): LifeCapacities = LifeCapacities(
        listening = world.listening,
        speaking = world.speaking,
        vocabulary = world.vocabulary,
        fluency = world.fluency,
        pragmatics = world.pragmatics,
        adaptability = world.adaptability,
        comprehension = world.comprehension
    )

    fun pendingStorySceneForCurrentWorld(world: WorldStateEntity): SceneNode? {
        val completed = world.completedSceneIdsCsv.split(",").filter { it.isNotBlank() }.toSet()
        return NewHavenWorldCatalog.firstPendingSceneForDay(world.currentDay, completed)
    }

    fun openLocationOrStory(locationId: LocationId) {
        val world = worldState.value
        val pending = pendingStorySceneForCurrentWorld(world)
        val scene = if (pending != null && pending.locationId == locationId) {
            pending
        } else {
            NewHavenWorldCatalog.freeRoamSceneForLocation(locationId, world.currentDay)
        }
        openScene(scene)
    }

    fun openSceneById(sceneId: String) {
        openScene(NewHavenWorldCatalog.sceneById(sceneId))
    }

    fun openScene(scene: SceneNode) {
        val world = worldState.value
        val completed = world.completedSceneIdsCsv.split(",").filter { it.isNotBlank() }.toSet()
        val alreadyDone = scene.isStoryBeat && scene.id in completed
        _sceneUiState.value = SceneUiState(
            scene = scene,
            supportLevel = SupportLevel.DISCOVER,
            observeCuesEnabled = false,
            choiceResolved = alreadyDone || scene.choices.isEmpty(),
            pendingNextSceneId = null,
            readyForDaySummary = false
        )
        viewModelScope.launch {
            repository.ensureSceneIntroLogged(scene)
        }
    }

    fun toggleObserveCues() {
        val current = _sceneUiState.value
        _sceneUiState.value = current.copy(observeCuesEnabled = !current.observeCuesEnabled)
    }

    fun advanceMeaningSupport() {
        val current = _sceneUiState.value
        val scene = current.scene ?: return
        val clue = scene.highlightedClue ?: return
        val nextLevel = when (current.supportLevel) {
            SupportLevel.DISCOVER -> SupportLevel.HINT
            SupportLevel.HINT -> SupportLevel.REVEAL
            SupportLevel.REVEAL -> SupportLevel.REVEAL
        }
        _sceneUiState.value = current.copy(supportLevel = nextLevel)
        if (nextLevel == SupportLevel.REVEAL) {
            viewModelScope.launch {
                repository.recordDiscoveredClue(
                    clueId = clue.id,
                    locationTitle = scene.locationId.title,
                    day = scene.day,
                    supportLevel = nextLevel
                )
            }
        }
    }

    fun selectChoice(choice: DialogueChoice) {
        val current = _sceneUiState.value
        val scene = current.scene ?: return
        viewModelScope.launch {
            val outcome = repository.applyDialogueChoice(scene, choice, current.supportLevel)
            _sceneUiState.value = _sceneUiState.value.copy(
                choiceResolved = true,
                pendingNextSceneId = outcome.nextSceneId,
                readyForDaySummary = outcome.dayCompleted,
                latestOutcomeNote = choice.worldReactionNote
            )
        }
    }

    fun continueToNextBeatOrFinish(onOpenDaySummary: () -> Unit) {
        val current = _sceneUiState.value
        val nextId = current.pendingNextSceneId
        if (!nextId.isNullOrBlank()) {
            openSceneById(nextId)
        } else if (current.readyForDaySummary) {
            onOpenDaySummary()
        }
    }

    fun sendFreeFormEnglish(text: String) {
        val trimmed = text.trim()
        val scene = _sceneUiState.value.scene ?: return
        if (trimmed.isBlank() || _sceneUiState.value.isSendingAi) return

        viewModelScope.launch {
            _sceneUiState.value = _sceneUiState.value.copy(isSendingAi = true, aiError = null)
            try {
                repository.sendUnscriptedEnglish(scene, trimmed)
                _sceneUiState.value = _sceneUiState.value.copy(isSendingAi = false)
            } catch (e: Exception) {
                _sceneUiState.value = _sceneUiState.value.copy(
                    isSendingAi = false,
                    aiError = e.localizedMessage ?: "Could not deliver message."
                )
            }
        }
    }

    fun advanceToNextDay(onComplete: () -> Unit) {
        viewModelScope.launch {
            repository.advanceToNextDay()
            onComplete()
        }
    }

    fun resetWorld(onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            repository.resetWorld()
            _sceneUiState.value = SceneUiState()
            onComplete()
        }
    }
}
