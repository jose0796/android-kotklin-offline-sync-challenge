package com.theempire.fielform.domain.visits.usecases

import com.theempire.fielform.model.Visit

interface SaveVisitUseCase {
    suspend operator fun invoke(visit: Visit)
}