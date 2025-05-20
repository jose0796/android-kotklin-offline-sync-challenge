package com.theempire.fieldform.data.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visits")
data class Visit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val siteName: String,
    val agentName: String,
    val comment: String,
    val status: String = "PENDING" // Estados posibles: PENDING, SENT, ERROR
)
