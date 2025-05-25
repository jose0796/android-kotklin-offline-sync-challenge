package com.theempire.fielform.local.visits

import androidx.room.TypeConverter

class VisitStatusEntityConverter {
    @TypeConverter
    fun fromStatus(status: VisitStatusEntity): String {
        return status.string()
    }

    @TypeConverter
    fun toStatus(value: String): VisitStatusEntity {
        return VisitStatusEntity.fromString(value)
    }
}