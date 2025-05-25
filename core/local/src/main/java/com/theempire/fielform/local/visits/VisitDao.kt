package com.theempire.fielform.local.visits

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visit: VisitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(visits: List<VisitEntity>)

    @Query("SELECT * FROM visits")
    fun getAll(): PagingSource<Int, VisitEntity>

    @Query("SELECT * FROM visits WHERE status = :status")
    fun getByStatus(status: String): PagingSource<Int, VisitEntity>

    @Query("SELECT * FROM visits WHERE status = :status")
    suspend fun getAllByStatus(status: String): List<VisitEntity>

    @Update
    suspend fun updateAll(visit: List<VisitEntity>)
}