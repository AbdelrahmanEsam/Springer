package com.first.springer.room

import android.app.Application
import androidx.lifecycle.LiveData
import com.first.springer.ModelResults
import io.reactivex.schedulers.Schedulers

class Repositry(application: Application?) {
    private val modelDAO: ModelDao

    init {
        val instanced = ResultsDatabase.getInstanced(application!!)
        modelDAO = instanced!!.ModelsDoa()!!
    }

    fun insert(modelResults: ModelResults?) {
        modelDAO.insert(modelResults)!!.subscribeOn(Schedulers.io()).subscribe()
    }

    fun delete(modelResults: ModelResults?) {
        modelDAO.delete(modelResults)!!.subscribeOn(Schedulers.io()).subscribe()
    }

    fun update(modelResults: ModelResults?) {
        modelDAO.update(modelResults)!!.subscribeOn(Schedulers.io()).subscribe()
    }

    fun deleteall() {
        modelDAO.deleteall()!!.subscribeOn(Schedulers.io()).subscribe()
    }

    fun getall(): LiveData<List<ModelResults?>?>? {
        return modelDAO.selectall()
    }


}