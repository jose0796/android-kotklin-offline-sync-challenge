package com.theempire.fielform.domain.visits.usecases

import androidx.paging.PagingData
import com.theempire.fielform.model.Visit
import kotlinx.coroutines.flow.Flow

interface GetAllVisitsUseCase {
    operator fun invoke() : Flow<PagingData<Visit>>
}