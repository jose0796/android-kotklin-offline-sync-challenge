package com.theempire.fieldform.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VisitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(visit: Visit)

    @Query("SELECT * FROM visits")
    fun getAll(): LiveData<List<Visit>>

    @Query("SELECT * FROM visits WHERE status = 'PENDING'")
    suspend fun getPending(): List<Visit>

    @Update
    suspend fun update(visit: Visit)
}
