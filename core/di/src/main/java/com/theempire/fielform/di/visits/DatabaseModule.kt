package com.theempire.fielform.di.visits

import android.content.Context
import androidx.room.Room
import com.theempire.fielform.local.database.FieldFormDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : FieldFormDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            FieldFormDatabase::class.java,
            "visit_db"
        ).build()

    @Provides
    @Singleton
    fun provideVisitsDao(database: FieldFormDatabase) = database.visitDao()

}