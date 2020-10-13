package com.apptikar.springer.di

import android.content.Context
import androidx.room.Room
import com.apptikar.springer.room.ResultsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule
{
    @Singleton
    @Provides
    fun provideRunningDatabase(@ApplicationContext app : Context) = Room.databaseBuilder(app,ResultsDatabase::class.java,"ResultsDatabase").build()

    @Singleton
    @Provides
    fun provideRunDao(db: ResultsDatabase) = db.getModelsDoa()


}