package com.first.springer.room
import androidx.lifecycle.LiveData
import androidx.room.*
import com.first.springer.ModelResults
import io.reactivex.Completable

@Dao
interface ModelDao {
    @Insert
    fun insert(modelResults: ModelResults?): Completable?

    @Delete
    fun delete(modelResults: ModelResults?): Completable?

    @Update
    fun update(modelResults: ModelResults?): Completable?

    @Query("DELETE FROM ModelResults")
    fun deleteall(): Completable?

    @Query("SELECT * FROM  ModelResults")
    fun selectall(): LiveData<List<ModelResults?>?>?
}