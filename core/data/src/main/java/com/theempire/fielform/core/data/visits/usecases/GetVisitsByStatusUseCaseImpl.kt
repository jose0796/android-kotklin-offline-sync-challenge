package com.theempire.fielform.core.data.visits.usecases

import androidx.paging.PagingData
import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.domain.visits.usecases.GetVisitsByStatusUseCase
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVisitsByStatusUseCaseImpl @Inject constructor(
    private val repository: VisitRepository
): GetVisitsByStatusUseCase{
    override fun invoke(status: VisitStatus): Flow<PagingData<Visit>> = repository.getVisitsByStatus(status)
}