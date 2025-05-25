package com.theempire.fielform.core.data.visits.usecases

import androidx.paging.PagingData
import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.domain.visits.usecases.GetAllVisitsUseCase
import com.theempire.fielform.model.Visit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllVisitsUseCaseImpl @Inject constructor(
    private val visitRepository: VisitRepository
) : GetAllVisitsUseCase {
    override fun invoke(): Flow<PagingData<Visit>> = visitRepository.getAllVisits()
}