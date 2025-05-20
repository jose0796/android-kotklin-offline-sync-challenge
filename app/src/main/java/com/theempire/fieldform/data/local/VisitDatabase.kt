package com.theempire.fieldform.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Visit::class], version = 1)
abstract class VisitDatabase : RoomDatabase() {
    abstract fun visitDao(): VisitDao

    companion object {
        @Volatile private var instance: VisitDatabase? = null

        fun getDatabase(context: Context): VisitDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    VisitDatabase::class.java,
                    "visit_db"
                ).build().also { instance = it }
            }
    }
}
