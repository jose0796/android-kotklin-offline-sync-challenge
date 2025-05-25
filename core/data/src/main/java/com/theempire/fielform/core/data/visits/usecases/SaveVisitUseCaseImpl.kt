package com.theempire.fielform.core.data.visits.usecases

import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.domain.visits.usecases.SaveVisitUseCase
import com.theempire.fielform.model.Visit
import javax.inject.Inject

class SaveVisitUseCaseImpl @Inject constructor(
    private val repository: VisitRepository
) : SaveVisitUseCase{
    override suspend fun invoke(visit: Visit) = repository.saveVisit(visit)
}