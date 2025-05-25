package com.theempire.fielform.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.theempire.fielform.local.visits.VisitDao
import com.theempire.fielform.local.visits.VisitEntity


@Database(entities = [VisitEntity::class], version = 1)
abstract class FieldFormDatabase : RoomDatabase() {
    abstract fun visitDao(): VisitDao
}
