package ru.cyclone.cycfinans.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TargetRepositoryImpl {
    @Insert
    suspend fun insertTarget(target: Target)

    @Query("SELECT * FROM target")
    suspend fun getAllTargets(): List<Target>

    @Update
    suspend fun updateTarget(target: Target)

    @Query("SELECT * FROM target WHERE id=:targetId")
    suspend fun getTargetById(targetId: Long)
}