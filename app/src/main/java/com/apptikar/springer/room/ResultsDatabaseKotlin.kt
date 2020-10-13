package com.apptikar.springer.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apptikar.springer.ModelResults


@Database(entities = [ModelResults::class], version = 1, exportSchema = false)
abstract  class ResultsDatabase : RoomDatabase()
{
  companion object
  {
    fun getInstanced (application: Application): ResultsDatabase {
     return  Room.databaseBuilder(application, ResultsDatabase::class.java, "ResultsDatabase").build()
    }

  }
  abstract fun  getModelsDoa() : ModelDao
}