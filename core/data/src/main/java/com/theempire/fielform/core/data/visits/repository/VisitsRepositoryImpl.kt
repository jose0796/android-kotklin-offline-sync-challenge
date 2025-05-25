package com.theempire.fielform.core.data.visits.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.theempire.fielform.core.data.visits.mappers.toDomain
import com.theempire.fielform.core.data.visits.mappers.toDto
import com.theempire.fielform.core.data.visits.mappers.toEntity
import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.local.visits.VisitDao
import com.theempire.fielform.local.visits.VisitStatusEntity
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import com.theempire.fielform.network.visits.service.VisitApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VisitsRepositoryImpl @Inject constructor(
    private val visitsDao: VisitDao,
    private val apiService: VisitApiService
): VisitRepository {

    override fun getVisitsByStatus(status: VisitStatus): Flow<PagingData<Visit>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { visitsDao.getByStatus(status.string()) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getAllVisitsByStatus(status: VisitStatus): List<Visit> =
        visitsDao.getAllByStatus(status.string()).map { it.toDomain() }

    override fun getAllVisits(): Flow<PagingData<Visit>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { visitsDao.getAll() }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun uploadVisit(visit: Visit): Boolean =
        try {
            apiService.syncVisits(visit.toDto())
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }

    override suspend fun markVisitsAsUploaded(visits: List<Visit>): Boolean =
        try {
            val sentVisits = visits.map { visit -> visit.toEntity(id = visit.id) }
            visitsDao.insertAll(sentVisits.map {  it.copy(status = VisitStatusEntity.Sent) })
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }

    override suspend fun markVisitsAsError(visits: List<Visit>): Boolean =
        try {
            val sentVisits = visits.map { visit -> visit.toEntity(id = visit.id) }
            visitsDao.insertAll(sentVisits.map {  it.copy(status = VisitStatusEntity.Error) })
            true
        }catch (e: Exception){
            e.printStackTrace()
            false
        }



    override suspend fun saveVisit(visit: Visit) {
        visitsDao.insert(visit.toEntity())
    }
}
