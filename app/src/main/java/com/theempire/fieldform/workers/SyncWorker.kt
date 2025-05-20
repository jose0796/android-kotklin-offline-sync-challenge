package com.theempire.fieldform.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.theempire.fieldform.data.local.Visit
import com.theempire.fieldform.data.local.VisitDatabase
import com.theempire.fieldform.data.repository.VisitRepository
import kotlinx.coroutines.delay

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val db = VisitDatabase.getDatabase(applicationContext)
        val repo = VisitRepository(db.visitDao())

        val pendingVisits = repo.getPending()

        pendingVisits.forEach { visit ->
            try {
                delay(500)
                repo.update(visit.copy(status = "SENT"))
            } catch (e: Exception) {
                repo.update(visit.copy(status = "ERROR"))
            }
        }
        return Result.success()
    }
}
