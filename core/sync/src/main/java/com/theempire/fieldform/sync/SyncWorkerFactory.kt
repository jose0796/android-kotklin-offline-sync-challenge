package com.theempire.fieldform.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.theempire.fielform.domain.visits.repository.VisitRepository
import javax.inject.Inject

class SyncWorkerFactory @Inject constructor(
    private val repository: VisitRepository
): WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = SyncVisitsWorker(repository, appContext, workerParameters)
}