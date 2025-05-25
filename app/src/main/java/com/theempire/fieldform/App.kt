package com.theempire.fieldform

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.theempire.fieldform.sync.SyncWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject lateinit var workerFactory: SyncWorkerFactory

    override val workManagerConfiguration: Configuration
        get() {
            Log.d("MyApp", "✅ Custom WorkManager configuration is being used")
            return Configuration.Builder()
                .setMinimumLoggingLevel(Log.INFO)
                .setWorkerFactory(workerFactory).build()
        }

}