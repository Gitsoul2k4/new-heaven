package com.example.domain.model

import androidx.annotation.DrawableRes
import com.example.R

enum class LocationId(
    val title: String,
    val subtitle: String,
    val streetLabel: String,
    @DrawableRes val sceneRes: Int
) {
    APARTMENT(
        title = "Maple St. Apartment",
        subtitle = "Home, neighbors & daily favors",
        streetLabel = "42 Maple St",
        sceneRes = R.drawable.img_scene_apartment
    ),
    CAFE(
        title = "Sunbeam Café",
        subtitle = "Orders, mishaps & job opportunities",
        streetLabel = "Corner of 3rd & Oak",
        sceneRes = R.drawable.img_scene_cafe
    ),
    GROCERY(
        title = "Corner Grocery",
        subtitle = "Supplies, budgeting & problem solving",
        streetLabel = "18 Harbor Ave",
        sceneRes = R.drawable.img_scene_park_market
    ),
    PARK(
        title = "Harbor Park",
        subtitle = "Conversations, nuance & community",
        streetLabel = "Harbor Waterfront",
        sceneRes = R.drawable.img_scene_park_market
    )
}

enum class LifeStage(
    val rank: Int,
    val title: String,
    val subtitle: String
) {
    SURVIVAL(1, "Survival", "Handling essentials: ordering, groceries, asking for help"),
    CONNECTION(2, "Connection", "Making neighbors, sharing stories & reading tone"),
    INDEPENDENCE(3, "Independence", "Working shifts, solving mix-ups & paraphrasing"),
    INFLUENCE(4, "Influence", "Persuading managers & framing what matters"),
    MASTERY(5, "Mastery of Life", "Navigating nuance, subtext & New Haven community")
}

enum class NpcMood(val label: String, val emojiHint: String) {
    WARM("Warm", "Relaxed smile"),
    CURIOUS("Curious", "Leaning in"),
    APOLOGETIC("Apologetic", "Flustered, sincere"),
    BUSY("Busy", "Checking the clock"),
    SKEPTICAL("Skeptical", "Raised eyebrow"),
    GUARDED("Guarded", "Crossed arms"),
    IMPRESSED("Impressed", "Nods approvingly"),
    SARCASTIC("Ironic", "Dry half-smile")
}

enum class SupportLevel {
    DISCOVER,
    HINT,
    REVEAL
}

enum class CommunicativeAct(val label: String) {
    GREETING("Warm Greeting"),
    PRACTICAL_ASK("Practical Question"),
    VULNERABLE_SHARE("Open & Honest"),
    POLITE_REQUEST("Polite Request"),
    PARAPHRASE("Creative Paraphrase"),
    ASK_RECOMMENDATION("Ask Advice"),
    EMPATHIZE("De-escalate & Forgive"),
    ASSERT_BOUNDARY("Firm Boundary"),
    EXPLAIN_EXPERIENCE("Pitch Yourself"),
    FRAME_URGENCY("Frame Importance"),
    REPAIR_MISUNDERSTANDING("Repair Mix-up"),
    READ_SUBTEXT("Read Between Lines")
}

data class MeaningClue(
    val id: String,
    val phrase: String,
    val formNote: String,
    val contextualMeaning: String,
    val conceptMetaphor: String,
    val visualHint: String,
    val deepReveal: String,
    val exampleContext: String
)

data class DialogueChoice(
    val id: String,
    val text: String,
    val communicativeAct: CommunicativeAct,
    val trustDelta: Int = 0,
    val moneyDelta: Int = 0,
    val jobStatusUpdate: String? = null,
    val nextSceneId: String? = null,
    val completesCurrentBeat: Boolean = false,
    val unlocksClueId: String? = null,
    val memorySummary: String,
    val worldReactionNote: String,
    val boostListening: Int = 0,
    val boostSpeaking: Int = 0,
    val boostVocabulary: Int = 0,
    val boostFluency: Int = 0,
    val boostPragmatics: Int = 0,
    val boostAdaptability: Int = 0,
    val boostComprehension: Int = 0
)

data class SceneNode(
    val id: String,
    val day: Int,
    val chapterTitle: String,
    val locationId: LocationId,
    val npcId: String,
    val npcName: String,
    val npcRole: String,
    val initialMood: NpcMood,
    val bodyLanguageCue: String,
    val environmentAction: String,
    val npcLine: String,
    val highlightedClue: MeaningClue? = null,
    val subtextNote: String? = null,
    val choices: List<DialogueChoice>,
    val allowsFreeInput: Boolean = true,
    val isStoryBeat: Boolean = true
)

data class NpcProfile(
    val id: String,
    val name: String,
    val role: String,
    val homeLocation: LocationId,
    val personality: String,
    val goal: String
)

data class LifeCapacities(
    val listening: Int = 10,
    val speaking: Int = 10,
    val vocabulary: Int = 10,
    val fluency: Int = 10,
    val pragmatics: Int = 8,
    val adaptability: Int = 12,
    val comprehension: Int = 10
) {
    val averageScore: Int
        get() = (listening + speaking + vocabulary + fluency + pragmatics + adaptability + comprehension) / 7

    fun toStage(): LifeStage = when {
        averageScore >= 75 -> LifeStage.MASTERY
        averageScore >= 55 -> LifeStage.INFLUENCE
        averageScore >= 38 -> LifeStage.INDEPENDENCE
        averageScore >= 22 -> LifeStage.CONNECTION
        else -> LifeStage.SURVIVAL
    }
}

data class ConversationBubble(
    val id: Long = 0L,
    val speakerName: String,
    val isPlayer: Boolean,
    val message: String,
    val actionTag: String? = null,
    val cueNote: String? = null,
    val isPlaceholderAi: Boolean = false
)
