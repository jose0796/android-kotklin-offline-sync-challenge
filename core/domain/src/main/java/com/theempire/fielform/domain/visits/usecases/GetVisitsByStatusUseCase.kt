package com.theempire.fielform.domain.visits.usecases

import androidx.paging.PagingData
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import kotlinx.coroutines.flow.Flow

interface GetVisitsByStatusUseCase {
    operator fun invoke(status: VisitStatus) : Flow<PagingData<Visit>>
}