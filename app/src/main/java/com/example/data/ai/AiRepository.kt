package com.example.data.ai

import com.example.domain.model.CommunicativeAct
import com.example.domain.model.NpcMood
import com.example.domain.model.NpcProfile
import kotlinx.coroutines.delay

data class NpcDialogueRequest(
    val npc: NpcProfile,
    val day: Int,
    val sceneTitle: String,
    val currentMood: NpcMood,
    val currentTrust: Int,
    val worldMemorySummary: String,
    val recentLines: List<String>,
    val playerMessage: String
)

data class AiEvaluationResult(
    val npcReply: String,
    val updatedMood: NpcMood,
    val bodyLanguageCue: String,
    val detectedAct: CommunicativeAct,
    val trustDelta: Int,
    val paraphraseDetected: Boolean,
    val pragmaticObservation: String,
    val memorySummary: String,
    val isPlaceholderSample: Boolean = true
)

/**
 * Single AI boundary routing the 4-system architecture (NPC AI, Context AI,
 * World AI, Language Assessment AI). Backed by PlaceholderAiRepository until
 * Firebase AI Logic is enabled.
 */
interface AiRepository {
    val isPlaceholderMode: Boolean
    suspend fun respondAsNpc(request: NpcDialogueRequest): AiEvaluationResult
}

class PlaceholderAiRepository : AiRepository {
    override val isPlaceholderMode: Boolean = true

    override suspend fun respondAsNpc(request: NpcDialogueRequest): AiEvaluationResult {
        delay(450)
        val raw = request.playerMessage.trim()
        val lower = raw.lowercase()

        val isPolite = listOf("please", "could i", "would you", "thank", "sorry", "excuse me", "appreciate", "mind if")
            .any { lower.contains(it) }
        val isParaphrase = listOf(
            "money back", "paid for this", "don't want it", "strong", "not too bitter",
            "something warm", "something cold", "worked at", "helped", "used to", "don't know the word"
        ).any { lower.contains(it) }
        val isBluntOrRude = listOf("shut up", "stupid", "give me", "hurry up", "whatever", "your fault", "hate")
            .any { lower.contains(it) }
        val isQuestion = lower.contains("?") || lower.startsWith("what") || lower.startsWith("where") ||
            lower.startsWith("how") || lower.startsWith("can") || lower.startsWith("could") || lower.startsWith("do you")
        val isApologyOrForgive = listOf("no problem", "it's okay", "its okay", "don't worry", "accidents happen", "all good", "sorry")
            .any { lower.contains(it) }

        val detectedAct = when {
            isParaphrase -> CommunicativeAct.PARAPHRASE
            isApologyOrForgive -> CommunicativeAct.EMPATHIZE
            isBluntOrRude -> CommunicativeAct.ASSERT_BOUNDARY
            isPolite -> CommunicativeAct.POLITE_REQUEST
            isQuestion -> CommunicativeAct.PRACTICAL_ASK
            else -> CommunicativeAct.VULNERABLE_SHARE
        }

        val trustDelta = when {
            isBluntOrRude -> -1
            isParaphrase || isApologyOrForgive || isPolite -> 2
            raw.split(" ").size >= 4 -> 1
            else -> 1
        }

        val updatedMood = when {
            isBluntOrRude -> NpcMood.GUARDED
            isParaphrase -> NpcMood.IMPRESSED
            isApologyOrForgive -> NpcMood.WARM
            isPolite -> NpcMood.WARM
            isQuestion -> NpcMood.CURIOUS
            else -> request.currentMood
        }

        val npcReply = buildContextualReply(request, lower, isPolite, isParaphrase, isBluntOrRude, isApologyOrForgive, isQuestion)

        val cue = when (updatedMood) {
            NpcMood.IMPRESSED -> "Nods with genuine appreciation — you got your point across naturally."
            NpcMood.WARM -> "Relaxes their shoulders and smiles warmly."
            NpcMood.CURIOUS -> "Tilts head attentively, waiting to hear more."
            NpcMood.GUARDED -> "Steps back slightly, keeping a polite distance."
            else -> "Maintains steady eye contact."
        }

        val observation = when {
            isParaphrase -> "Adaptability noticed: You explained your idea with the words you already had instead of stopping."
            isBluntOrRude -> "Pragmatics signal: Direct phrasing came across tense in this situation."
            isPolite -> "Pragmatics signal: Courteous framing eased the interaction."
            else -> "Fluency signal: You kept the conversation moving naturally in your own words."
        }

        val memorySnippet = "Day ${request.day}: You said \"${raw.take(48)}${if (raw.length > 48) "..." else ""}\" (${detectedAct.label})."

        return AiEvaluationResult(
            npcReply = "[Sample AI Mode] $npcReply",
            updatedMood = updatedMood,
            bodyLanguageCue = cue,
            detectedAct = detectedAct,
            trustDelta = trustDelta,
            paraphraseDetected = isParaphrase,
            pragmaticObservation = observation,
            memorySummary = memorySnippet,
            isPlaceholderSample = true
        )
    }

    private fun buildContextualReply(
        request: NpcDialogueRequest,
        lower: String,
        isPolite: Boolean,
        isParaphrase: Boolean,
        isBlunt: Boolean,
        isForgive: Boolean,
        isQuestion: Boolean
    ): String {
        if (isBlunt) {
            return when (request.npc.id) {
                "vance" -> "Whoa, easy there. Here at Sunbeam Café we keep things calm with customers and teammates."
                "sam" -> "I—yeah, okay, I hear you. I'll get out of your hair."
                else -> "Okay, let's take a breath. I wasn't trying to make things harder."
            }
        }
        return when (request.npc.id) {
            "alex" -> when {
                lower.contains("store") || lower.contains("grocery") || lower.contains("food") ->
                    "Corner Grocery is just two blocks down on Harbor Ave! Elena runs it — tell her Alex from 4B sent you."
                lower.contains("job") || lower.contains("work") ->
                    "Oh! I saw a 'Part-Time Barista' sign at Sunbeam Café this morning. Worth walking over!"
                isQuestion ->
                    "Great question. Honestly, New Haven feels huge on day one, so take it block by block. Wanna grab coffee later?"
                else ->
                    "I totally get that. Moving somewhere new takes guts — knock on 4B anytime if you need a hand."
            }
            "maya" -> when {
                isParaphrase && (lower.contains("strong") || lower.contains("bitter") || lower.contains("sweet")) ->
                    "Ah, gotcha! Sounds like a flat white or an oat cortado — rich espresso, smooth milk, not bitter at all. Coming right up!"
                lower.contains("coffee") || lower.contains("tea") || lower.contains("latte") ->
                    "Perfect choice! I'll make that fresh for you right now. That'll be $4."
                isPolite ->
                    "Of course! Take your time looking at the board — I'm happy to customize anything."
                else ->
                    "Hear you loud and clear! I'll whip up our house favorite for you."
            }
            "sam" -> when {
                isForgive ->
                    "Whew, thank you for being so cool about it. Let me at least buy your next coffee to make it up to you!"
                else ->
                    "Seriously, I'm so embarrassed. Here, grab these napkins — and please let me pay for your drink."
            }
            "vance" -> when {
                lower.contains("hard") || lower.contains("learn") || lower.contains("experience") || lower.contains("help") || isParaphrase ->
                    "I respect honesty and hustle more than a fancy resume. Can you come in for a paid trial shift tomorrow?"
                lower.contains("important") || lower.contains("serious") || lower.contains("critical") || lower.contains("afford") ->
                    "Okay, you've got my full attention. Let's sit down and fix the schedule before the rush hits."
                else ->
                    "Interesting point. Show me how you handle customers on the floor, and we'll take it from there."
            }
            "elena" -> when {
                isParaphrase || lower.contains("money back") || lower.contains("wrong") || lower.contains("return") ->
                    "Oh, you want a refund! No worries at all — since you have the receipt, I can put the $6 right back."
                isQuestion ->
                    "Fresh produce is right by the window, and staples are in aisle two. Let me know if you're looking for anything specific."
                else ->
                    "You're doing great! Take what you need for the week — I'll pack it light for the walk home."
            }
            else -> when {
                lower.contains("mean") || lower.contains("sarcas") || lower.contains("serious") ->
                    "Haha, fair catch! Yeah, I was being a little ironic — glad you picked up on that."
                isQuestion ->
                    "Good way to put it. Around Harbor Park, people appreciate when you ask straight out."
                else ->
                    "That actually makes a lot of sense. Funny how fast New Haven starts feeling familiar, right?"
            }
        }
    }
}
