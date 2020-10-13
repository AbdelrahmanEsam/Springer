package com.apptikar.springer.room
import androidx.lifecycle.LiveData
import com.apptikar.springer.ModelResults
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject

class Repositry @Inject constructor(val modelDao: ModelDao) {




    fun insert(modelResults: ModelResults?) {
        CoroutineScope(IO).launch {
           modelDao.insert(modelResults)
       }
    }

    fun delete(modelResults: ModelResults?) {
        CoroutineScope(IO).launch {
            modelDao.delete(modelResults)
        }
    }

    fun update(modelResults: ModelResults?) {
      CoroutineScope(IO).launch {
          modelDao.update(modelResults)
      }
    }

    fun deleteAll() {
        CoroutineScope(IO).launch {
            modelDao.deleteall()
        }
    }

    fun getAll(): LiveData<List<ModelResults?>?>? {
        return modelDao.selectall()
    }


}