package com.theempire.fielform.network.visits.service

import com.theempire.fielform.network.visits.dto.VisitDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VisitApiService {
    @POST("post")
    suspend fun syncVisits(@Body visit: VisitDto): Response<Unit>
}