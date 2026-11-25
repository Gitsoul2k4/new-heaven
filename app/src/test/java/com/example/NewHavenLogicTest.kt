package com.example

import com.example.data.ai.NpcDialogueRequest
import com.example.data.ai.PlaceholderAiRepository
import com.example.data.story.NewHavenWorldCatalog
import com.example.domain.model.CommunicativeAct
import com.example.domain.model.LifeCapacities
import com.example.domain.model.LifeStage
import com.example.domain.model.NpcMood
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NewHavenLogicTest {

    @Test
    fun `all 7 days have valid pending starter scenes`() {
        for (day in 1..7) {
            val scene = NewHavenWorldCatalog.firstPendingSceneForDay(day, emptySet())
            assertNotNull("Day $day should have a starter scene", scene)
        }
    }

    @Test
    fun `placeholder ai recognizes paraphrase without refund keyword`() = runTest {
        val repo = PlaceholderAiRepository()
        val elena = NewHavenWorldCatalog.npcs.getValue("elena")
        val result = repo.respondAsNpc(
            NpcDialogueRequest(
                npc = elena,
                day = 2,
                sceneTitle = "Corner Grocery",
                currentMood = NpcMood.CURIOUS,
                currentTrust = 1,
                worldMemorySummary = "",
                recentLines = emptyList(),
                playerMessage = "I paid for this, but I don't want it anymore. Can I get my money back?"
            )
        )
        assertTrue(result.paraphraseDetected)
        assertEquals(CommunicativeAct.PARAPHRASE, result.detectedAct)
        assertTrue(result.isPlaceholderSample)
    }

    @Test
    fun `life capacities map to expected life stages`() {
        assertEquals(LifeStage.SURVIVAL, LifeCapacities().toStage())
        assertEquals(
            LifeStage.INDEPENDENCE,
            LifeCapacities(40, 40, 40, 40, 40, 40, 40).toStage()
        )
        assertEquals(
            LifeStage.MASTERY,
            LifeCapacities(80, 80, 80, 80, 80, 80, 80).toStage()
        )
    }
}
