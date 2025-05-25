package com.theempire.fielform.domain.visits.repository

import androidx.paging.PagingData
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import kotlinx.coroutines.flow.Flow

interface VisitRepository {
    fun getVisitsByStatus(status: VisitStatus): Flow<PagingData<Visit>>
    fun getAllVisits(): Flow<PagingData<Visit>>
    suspend fun getAllVisitsByStatus(status: VisitStatus): List<Visit>
    suspend fun uploadVisit(visit: Visit): Boolean
    suspend fun markVisitsAsUploaded(visits: List<Visit>): Boolean
    suspend fun markVisitsAsError(visits: List<Visit>): Boolean
    suspend fun saveVisit(visit: Visit)
}