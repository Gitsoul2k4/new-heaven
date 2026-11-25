package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "world_state")
data class WorldStateEntity(
    @PrimaryKey val id: Int = 1,
    val currentDay: Int = 1,
    val moneyDollars: Int = 50,
    val jobStatus: String = "Looking for work",
    val reputation: Int = 0,
    val completedSceneIdsCsv: String = "",
    val activeSceneId: String = "day1_neighbor_alex",
    val listening: Int = 12,
    val speaking: Int = 12,
    val vocabulary: Int = 10,
    val fluency: Int = 10,
    val pragmatics: Int = 10,
    val adaptability: Int = 14,
    val comprehension: Int = 12,
    val unpromptedEnglishCount: Int = 0,
    val paraphraseSuccessCount: Int = 0
)

@Entity(tableName = "npc_relationships")
data class NpcRelationshipEntity(
    @PrimaryKey val npcId: String,
    val name: String,
    val role: String,
    val locationId: String,
    val trustScore: Int = 1,
    val relationshipTier: String = "New Face",
    val lastMood: String = "WARM",
    val memorySummary: String = "Haven't spoken deeply yet.",
    val interactionsCount: Int = 0
)

@Entity(tableName = "language_memories")
data class LanguageMemoryEntity(
    @PrimaryKey val clueId: String,
    val phrase: String,
    val formNote: String,
    val contextualMeaning: String,
    val conceptMetaphor: String,
    val discoveredLocation: String,
    val discoveredDay: Int,
    val supportUsed: String,
    val reusedInContextCount: Int = 0
)

@Entity(tableName = "conversation_logs")
data class ConversationLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val sceneId: String,
    val day: Int,
    val speakerName: String,
    val isPlayer: Boolean,
    val message: String,
    val actionTag: String?,
    val cueNote: String?,
    val isPlaceholderAi: Boolean,
    val timestampMillis: Long = System.currentTimeMillis()
)

@Entity(tableName = "day_milestones")
data class DayMilestoneEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val day: Int,
    val title: String,
    val subtitle: String,
    val discoveredPhrase: String? = null,
    val timestampMillis: Long = System.currentTimeMillis()
)
