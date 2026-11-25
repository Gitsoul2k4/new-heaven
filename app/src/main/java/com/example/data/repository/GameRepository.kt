package com.example.data.repository

import com.example.data.ai.AiEvaluationResult
import com.example.data.ai.AiRepository
import com.example.data.ai.NpcDialogueRequest
import com.example.data.local.ConversationLogEntity
import com.example.data.local.DayMilestoneEntity
import com.example.data.local.GameDao
import com.example.data.local.LanguageMemoryEntity
import com.example.data.local.NpcRelationshipEntity
import com.example.data.local.WorldStateEntity
import com.example.data.story.NewHavenWorldCatalog
import com.example.domain.model.DialogueChoice
import com.example.domain.model.NpcMood
import com.example.domain.model.SceneNode
import com.example.domain.model.SupportLevel
import kotlinx.coroutines.flow.Flow

class GameRepository(
    private val dao: GameDao,
    private val aiRepository: AiRepository
) {
    val worldStateFlow: Flow<WorldStateEntity?> = dao.observeWorldState()
    val npcRelationshipsFlow: Flow<List<NpcRelationshipEntity>> = dao.observeNpcRelationships()
    val languageMemoriesFlow: Flow<List<LanguageMemoryEntity>> = dao.observeLanguageMemories()
    val milestonesFlow: Flow<List<DayMilestoneEntity>> = dao.observeMilestones()

    val isPlaceholderAiMode: Boolean
        get() = aiRepository.isPlaceholderMode

    fun observeSceneConversation(sceneId: String): Flow<List<ConversationLogEntity>> =
        dao.observeSceneConversation(sceneId)

    fun observeDayMilestones(day: Int): Flow<List<DayMilestoneEntity>> =
        dao.observeMilestonesForDay(day)

    suspend fun ensureInitialized() {
        if (dao.getWorldStateOnce() == null) {
            dao.upsertWorldState(WorldStateEntity())
            val initialNpcs = NewHavenWorldCatalog.npcs.values.map { profile ->
                NpcRelationshipEntity(
                    npcId = profile.id,
                    name = profile.name,
                    role = profile.role,
                    locationId = profile.homeLocation.name,
                    trustScore = 1,
                    relationshipTier = trustToTier(1),
                    lastMood = NpcMood.WARM.name,
                    memorySummary = "New neighbor in New Haven — haven't talked deeply yet.",
                    interactionsCount = 0
                )
            }
            dao.upsertNpcs(initialNpcs)
        }
    }

    suspend fun ensureSceneIntroLogged(scene: SceneNode) {
        val existing = dao.getSceneConversationOnce(scene.id)
        if (existing.isEmpty()) {
            dao.insertConversationLog(
                ConversationLogEntity(
                    sceneId = scene.id,
                    day = scene.day,
                    speakerName = scene.npcName,
                    isPlayer = false,
                    message = scene.npcLine,
                    actionTag = null,
                    cueNote = scene.bodyLanguageCue,
                    isPlaceholderAi = false
                )
            )
        }
    }

    suspend fun recordDiscoveredClue(
        clueId: String,
        locationTitle: String,
        day: Int,
        supportLevel: SupportLevel
    ) {
        val clue = NewHavenWorldCatalog.clues[clueId] ?: return
        dao.upsertLanguageMemory(
            LanguageMemoryEntity(
                clueId = clue.id,
                phrase = clue.phrase,
                formNote = clue.formNote,
                contextualMeaning = clue.contextualMeaning,
                conceptMetaphor = clue.conceptMetaphor,
                discoveredLocation = locationTitle,
                discoveredDay = day,
                supportUsed = supportLevel.name
            )
        )
    }

    suspend fun applyDialogueChoice(
        scene: SceneNode,
        choice: DialogueChoice,
        supportUsed: SupportLevel
    ): ChoiceOutcomeResult {
        ensureInitialized()
        val world = dao.getWorldStateOnce() ?: WorldStateEntity()

        // Log player's spoken line and NPC's immediate world reaction
        dao.insertConversationLog(
            ConversationLogEntity(
                sceneId = scene.id,
                day = scene.day,
                speakerName = "You",
                isPlayer = true,
                message = choice.text,
                actionTag = choice.communicativeAct.label,
                cueNote = null,
                isPlaceholderAi = false
            )
        )
        dao.insertConversationLog(
            ConversationLogEntity(
                sceneId = scene.id,
                day = scene.day,
                speakerName = scene.npcName,
                isPlayer = false,
                message = choice.worldReactionNote,
                actionTag = null,
                cueNote = null,
                isPlaceholderAi = false
            )
        )

        // Update NPC relationship & world memory
        val existingNpc = dao.getNpcOnce(scene.npcId)
        val newTrust = ((existingNpc?.trustScore ?: 1) + choice.trustDelta).coerceIn(-5, 20)
        val updatedMood = if (choice.trustDelta < 0) NpcMood.GUARDED else NpcMood.WARM
        if (existingNpc != null) {
            dao.upsertNpc(
                existingNpc.copy(
                    trustScore = newTrust,
                    relationshipTier = trustToTier(newTrust),
                    lastMood = updatedMood.name,
                    memorySummary = choice.memorySummary,
                    interactionsCount = existingNpc.interactionsCount + 1
                )
            )
        }

        // Record language memory if clue unlocked
        var discoveredPhrase: String? = null
        choice.unlocksClueId?.let { clueId ->
            recordDiscoveredClue(clueId, scene.locationId.title, scene.day, supportUsed)
            discoveredPhrase = NewHavenWorldCatalog.clues[clueId]?.phrase
        }

        // Insert milestone for Day summary
        dao.insertMilestone(
            DayMilestoneEntity(
                day = scene.day,
                title = "${scene.npcName} · ${scene.locationId.title}",
                subtitle = choice.memorySummary,
                discoveredPhrase = discoveredPhrase
            )
        )

        // Update WorldState
        val completedSet = world.completedSceneIdsCsv
            .split(",")
            .filter { it.isNotBlank() }
            .toMutableSet()
        if (scene.isStoryBeat) {
            completedSet.add(scene.id)
        }

        val updatedWorld = world.copy(
            moneyDollars = (world.moneyDollars + choice.moneyDelta).coerceAtLeast(0),
            jobStatus = choice.jobStatusUpdate ?: world.jobStatus,
            reputation = (world.reputation + choice.trustDelta).coerceAtLeast(0),
            completedSceneIdsCsv = completedSet.joinToString(","),
            activeSceneId = choice.nextSceneId ?: world.activeSceneId,
            listening = (world.listening + choice.boostListening).coerceAtMost(100),
            speaking = (world.speaking + choice.boostSpeaking).coerceAtMost(100),
            vocabulary = (world.vocabulary + choice.boostVocabulary).coerceAtMost(100),
            fluency = (world.fluency + choice.boostFluency).coerceAtMost(100),
            pragmatics = (world.pragmatics + choice.boostPragmatics).coerceAtMost(100),
            adaptability = (world.adaptability + choice.boostAdaptability).coerceAtMost(100),
            comprehension = (world.comprehension + choice.boostComprehension).coerceAtMost(100),
            paraphraseSuccessCount = world.paraphraseSuccessCount +
                if (choice.communicativeAct.name == "PARAPHRASE") 1 else 0
        )
        dao.upsertWorldState(updatedWorld)

        return ChoiceOutcomeResult(
            nextSceneId = choice.nextSceneId,
            dayCompleted = choice.completesCurrentBeat
        )
    }

    suspend fun sendUnscriptedEnglish(
        scene: SceneNode,
        playerText: String
    ): AiEvaluationResult {
        ensureInitialized()
        val world = dao.getWorldStateOnce() ?: WorldStateEntity()
        val npcEntity = dao.getNpcOnce(scene.npcId)
        val npcProfile = NewHavenWorldCatalog.npcs[scene.npcId]
            ?: NewHavenWorldCatalog.npcs.values.first()
        val recent = dao.getSceneConversationOnce(scene.id).takeLast(4).map { "${it.speakerName}: ${it.message}" }

        dao.insertConversationLog(
            ConversationLogEntity(
                sceneId = scene.id,
                day = scene.day,
                speakerName = "You",
                isPlayer = true,
                message = playerText.trim(),
                actionTag = "Your English",
                cueNote = null,
                isPlaceholderAi = false
            )
        )

        val currentMood = NpcMood.entries.firstOrNull { it.name == npcEntity?.lastMood } ?: scene.initialMood
        val aiResult = aiRepository.respondAsNpc(
            NpcDialogueRequest(
                npc = npcProfile,
                day = world.currentDay,
                sceneTitle = scene.locationId.title,
                currentMood = currentMood,
                currentTrust = npcEntity?.trustScore ?: 1,
                worldMemorySummary = npcEntity?.memorySummary ?: "",
                recentLines = recent,
                playerMessage = playerText.trim()
            )
        )

        dao.insertConversationLog(
            ConversationLogEntity(
                sceneId = scene.id,
                day = scene.day,
                speakerName = scene.npcName,
                isPlayer = false,
                message = aiResult.npcReply,
                actionTag = aiResult.detectedAct.label,
                cueNote = "${aiResult.bodyLanguageCue} (${aiResult.pragmaticObservation})",
                isPlaceholderAi = aiResult.isPlaceholderSample
            )
        )

        if (npcEntity != null) {
            val newTrust = (npcEntity.trustScore + aiResult.trustDelta).coerceIn(-5, 20)
            dao.upsertNpc(
                npcEntity.copy(
                    trustScore = newTrust,
                    relationshipTier = trustToTier(newTrust),
                    lastMood = aiResult.updatedMood.name,
                    memorySummary = aiResult.memorySummary,
                    interactionsCount = npcEntity.interactionsCount + 1
                )
            )
        }

        dao.upsertWorldState(
            world.copy(
                reputation = (world.reputation + aiResult.trustDelta).coerceAtLeast(0),
                speaking = (world.speaking + 3).coerceAtMost(100),
                fluency = (world.fluency + 3).coerceAtMost(100),
                adaptability = (world.adaptability + if (aiResult.paraphraseDetected) 6 else 2).coerceAtMost(100),
                pragmatics = (world.pragmatics + if (aiResult.trustDelta > 0) 3 else 1).coerceAtMost(100),
                unpromptedEnglishCount = world.unpromptedEnglishCount + 1,
                paraphraseSuccessCount = world.paraphraseSuccessCount + if (aiResult.paraphraseDetected) 1 else 0
            )
        )

        return aiResult
    }

    suspend fun advanceToNextDay() {
        val world = dao.getWorldStateOnce() ?: return
        val nextDay = (world.currentDay + 1).coerceAtMost(7)
        val nextBeats = NewHavenWorldCatalog.dayBeatOrder[nextDay].orEmpty()
        dao.upsertWorldState(
            world.copy(
                currentDay = nextDay,
                activeSceneId = nextBeats.firstOrNull() ?: world.activeSceneId
            )
        )
    }

    suspend fun resetWorld() {
        dao.clearConversations()
        dao.clearMilestones()
        dao.clearLanguageMemories()
        dao.clearNpcs()
        dao.clearWorldState()
        ensureInitialized()
    }

    private fun trustToTier(trust: Int): String = when {
        trust >= 9 -> "Close Friend"
        trust >= 5 -> "Trusted Neighbor"
        trust >= 2 -> "Acquaintance"
        trust >= 0 -> "New Face"
        else -> "Strained"
    }
}

data class ChoiceOutcomeResult(
    val nextSceneId: String?,
    val dayCompleted: Boolean
)
