package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Query("SELECT * FROM world_state WHERE id = 1")
    fun observeWorldState(): Flow<WorldStateEntity?>

    @Query("SELECT * FROM world_state WHERE id = 1")
    suspend fun getWorldStateOnce(): WorldStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorldState(state: WorldStateEntity)

    @Query("SELECT * FROM npc_relationships ORDER BY trustScore DESC, name ASC")
    fun observeNpcRelationships(): Flow<List<NpcRelationshipEntity>>

    @Query("SELECT * FROM npc_relationships WHERE npcId = :npcId")
    suspend fun getNpcOnce(npcId: String): NpcRelationshipEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNpc(npc: NpcRelationshipEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertNpcs(npcs: List<NpcRelationshipEntity>)

    @Query("SELECT * FROM language_memories ORDER BY discoveredDay DESC, phrase ASC")
    fun observeLanguageMemories(): Flow<List<LanguageMemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertLanguageMemory(memory: LanguageMemoryEntity)

    @Query("SELECT * FROM conversation_logs WHERE sceneId = :sceneId ORDER BY id ASC")
    fun observeSceneConversation(sceneId: String): Flow<List<ConversationLogEntity>>

    @Query("SELECT * FROM conversation_logs WHERE sceneId = :sceneId ORDER BY id ASC")
    suspend fun getSceneConversationOnce(sceneId: String): List<ConversationLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversationLog(log: ConversationLogEntity)

    @Query("SELECT * FROM day_milestones ORDER BY day DESC, id DESC")
    fun observeMilestones(): Flow<List<DayMilestoneEntity>>

    @Query("SELECT * FROM day_milestones WHERE day = :day ORDER BY id ASC")
    fun observeMilestonesForDay(day: Int): Flow<List<DayMilestoneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: DayMilestoneEntity)

    @Query("DELETE FROM world_state")
    suspend fun clearWorldState()

    @Query("DELETE FROM npc_relationships")
    suspend fun clearNpcs()

    @Query("DELETE FROM language_memories")
    suspend fun clearLanguageMemories()

    @Query("DELETE FROM conversation_logs")
    suspend fun clearConversations()

    @Query("DELETE FROM day_milestones")
    suspend fun clearMilestones()
}
