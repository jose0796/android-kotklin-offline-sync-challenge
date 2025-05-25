package com.theempire.fielform.network.visits.dto

import kotlinx.serialization.Serializable

@Serializable
data class VisitDto(
    val id: Int,
    val siteName: String,
    val agentName: String,
    val comment: String
)