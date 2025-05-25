package com.theempire.fielform.local.visits

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "visits")
@TypeConverters(VisitStatusEntityConverter::class)
data class VisitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteName: String,
    val agentName: String,
    val comment: String,
    val status: VisitStatusEntity = VisitStatusEntity.Pending
)

sealed interface VisitStatusEntity {
    object Pending : VisitStatusEntity
    object Sent : VisitStatusEntity
    object Error : VisitStatusEntity

    fun string(): String = when (this) {
        Pending -> "PENDING"
        Sent -> "SENT"
        Error -> "ERROR"
    }

    companion object {
        fun fromString(value: String): VisitStatusEntity = when (value.uppercase()) {
            "SENT" -> Sent
            "ERROR" -> Error
            else -> Pending
        }
    }
}