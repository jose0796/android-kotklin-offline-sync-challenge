package com.theempire.fieldform.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.theempire.fielform.domain.visits.repository.VisitRepository
import com.theempire.fielform.model.Visit
import com.theempire.fielform.model.VisitStatus
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncVisitsWorker @AssistedInject constructor(
    val repository: VisitRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,

) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val pendingVisits = repository.getAllVisitsByStatus(status = VisitStatus.Pending)
            val failed = mutableListOf<Visit>()

            pendingVisits.forEach {
                val success = repository.uploadVisit(it)
                if (!success) failed.add(it)
            }

            val uploadedVisits = pendingVisits - failed
            repository.markVisitsAsUploaded(uploadedVisits)
            repository.markVisitsAsError(failed)

            if (failed.isEmpty()) Result.success() else Result.retry()
        }catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}