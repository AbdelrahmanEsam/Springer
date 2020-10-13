package com.apptikar.springer.room
import android.app.Application
import androidx.lifecycle.LiveData
import com.apptikar.springer.ModelResults
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class DatabaseViewModel  @ViewModelInject constructor(val repositry: Repositry) : ViewModel() {
    fun insert(modelResult: ModelResults?) {
        repositry.insert(modelResult)
    }

    fun deleteAll() {
        repositry.deleteAll()
    }

    fun delete(modelResult: ModelResults?) {
        repositry.delete(modelResult)
    }

    fun update(modelResult: ModelResults?) {
        repositry.update(modelResult)
    }

    fun getAll(): LiveData<List<ModelResults?>?>? {
        return repositry.getAll()
    }

}