package com.theempire.fielform.core.data.visits.sync

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.theempire.fieldform.sync.SyncVisitsWorker
import com.theempire.fielform.domain.visits.sync.UploadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class UploadManagerImpl @Inject constructor(
    private val context: Context
) : UploadManager {

    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun schedulePeriodicSync(
        onProgress: () -> Unit,
        onFinished: () -> Unit
    ) {
        val workRequest = PeriodicWorkRequestBuilder<SyncVisitsWorker>(5, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(BackoffPolicy.LINEAR, duration = 20.seconds.toJavaDuration())
            .build()

        val workRequestId = workRequest.id

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
            "sync_visits",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )

        WorkManager.getInstance(context).getWorkInfoByIdFlow(workRequestId)
            .onEach {
                when(it?.state) {
                    WorkInfo.State.RUNNING -> {
                        delay(1000)
                        onProgress()
                    }
                    WorkInfo.State.SUCCEEDED -> {
                        delay(1000)
                        onFinished()
                    }
                    WorkInfo.State.FAILED -> {
                        delay(1000)
                        onFinished()
                    }
                    WorkInfo.State.CANCELLED -> {
                        delay(1000)
                        onFinished()
                    }
                    else -> Unit
                }
            }.stateIn(scope, SharingStarted.WhileSubscribed(5_000), null)
    }

    override fun scheduleSync(onFinished: () -> Unit) {
        val request = OneTimeWorkRequestBuilder<SyncVisitsWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setBackoffCriteria(BackoffPolicy.LINEAR, duration = 20.seconds.toJavaDuration())
            .build()

        val workRequestId = request.id

        WorkManager.getInstance(context).enqueueUniqueWork(
            "sync-visits-unique",
            ExistingWorkPolicy.REPLACE,
            request
        )

        WorkManager.getInstance(context).getWorkInfoByIdFlow(workRequestId)
            .onEach {
                when(it?.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        delay(1000)
                        onFinished()
                    }
                    WorkInfo.State.FAILED -> {
                        delay(1000)
                        onFinished()
                    }
                    WorkInfo.State.CANCELLED -> {
                        delay(1000)
                        onFinished()
                    }
                    else -> Unit
                }
            }.stateIn(scope, SharingStarted.WhileSubscribed(5_000), null)


    }
}
