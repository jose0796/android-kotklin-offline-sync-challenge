package com.theempire.fielform.core.data.visits.mappers

import com.theempire.fielform.local.visits.VisitEntity
import com.theempire.fielform.local.visits.VisitStatusEntity
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import com.theempire.fielform.network.visits.dto.VisitDto

fun VisitEntity.toDomain(): Visit =
    Visit(
        id = id,
        siteName = siteName,
        agentName = agentName,
        comment = comment,
        status = VisitStatus.fromString(status.string())
    )

fun Visit.toEntity(): VisitEntity =
    VisitEntity(
        siteName = siteName,
        agentName = agentName,
        comment = comment,
        status = VisitStatusEntity.fromString(status.string())
    )

fun Visit.toEntity(id: Int): VisitEntity =
    VisitEntity(
        id = id,
        siteName = siteName,
        agentName = agentName,
        comment = comment,
        status = VisitStatusEntity.fromString(status.string())
    )

fun Visit.toDto(): VisitDto =
    VisitDto(
        id = id,
        siteName = siteName,
        agentName = agentName,
        comment = comment
    )
