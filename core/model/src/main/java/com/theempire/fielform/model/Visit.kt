package com.theempire.fielform.model

data class Visit(
    val id: Int = -1,
    val siteName: String,
    val agentName: String,
    val comment: String,
    val status: VisitStatus = VisitStatus.Pending
)

sealed interface VisitStatus {
    object Pending : VisitStatus
    object Sent : VisitStatus
    object Error : VisitStatus

    fun string(): String = when (this) {
        Pending -> "PENDING"
        Sent -> "SENT"
        Error -> "ERROR"
    }

    companion object {
        fun fromString(value: String): VisitStatus = when (value.uppercase()) {
            "SENT" -> Sent
            "ERROR" -> Error
            else -> Pending
        }
    }
}