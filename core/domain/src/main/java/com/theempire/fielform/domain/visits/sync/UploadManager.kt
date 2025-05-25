package com.theempire.fielform.domain.visits.sync

interface UploadManager {
    fun scheduleSync(onFinished: () -> Unit = {})
    fun schedulePeriodicSync(onProgress: () -> Unit = {}, onFinished: () -> Unit = {})
}