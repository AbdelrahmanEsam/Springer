package com.apptikar.springer.room
import androidx.lifecycle.LiveData
import androidx.room.*
import com.apptikar.springer.ModelResults
import io.reactivex.Completable

@Dao
interface ModelDao {

    @Insert
  suspend  fun insert(modelResults: ModelResults?)

    @Delete
  suspend  fun delete(modelResults: ModelResults?)

    @Update
  suspend  fun update(modelResults: ModelResults?)

    @Query("DELETE FROM ModelResults")
  suspend  fun deleteall()

    @Query("SELECT * FROM  ModelResults")
    fun selectall(): LiveData<List<ModelResults?>?>?
}