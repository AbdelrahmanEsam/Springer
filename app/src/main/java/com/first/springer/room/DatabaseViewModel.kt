package com.first.springer.room
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.first.springer.ModelResults

class DatabaseViewModel(application: Application) : AndroidViewModel(application) {
    private val repositry: Repositry = Repositry(application)
    fun insert(modelResult: ModelResults?) {
        repositry.insert(modelResult)
    }

    fun deleteall() {
        repositry.deleteall()
    }

    fun delete(modelResult: ModelResults?) {
        repositry.delete(modelResult)
    }

    fun update(modelResult: ModelResults?) {
        repositry.update(modelResult)
    }

    fun getall(): LiveData<List<ModelResults?>?>? {
        return repositry.getall()
    }

}