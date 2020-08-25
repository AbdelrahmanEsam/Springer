package com.first.springer.proccessactivites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class ProccessViewModel : ViewModel() {

    var MoldOrDie = MutableLiveData<String>()
    var MoldOrDieName = MutableLiveData<String>()
    var MoldOrDieCode = MutableLiveData<String>()
    var Color = MutableLiveData<String>()
    var SpringLength = MutableLiveData<String>()
    var SpringSpace = MutableLiveData<String>()
    var SpringNoOfSpace = MutableLiveData<String>()
    var SpringHousing = MutableLiveData<String>()
    var EjectionStroke = MutableLiveData<String>()
    var EditUri = MutableLiveData<String>()
    var EditName = MutableLiveData<String>()
    var EditEmail = MutableLiveData<String>()
    var EditAge = MutableLiveData<String>()
    var EditGender = MutableLiveData<String>()
    var EditPassword = MutableLiveData<String>()
    var percentprogressfinal = MutableLiveData<String>()
    var maxprogressfinal = MutableLiveData<String>()
    var CompressionDistancefinal = MutableLiveData<String>()
    var ResultUri = MutableLiveData<String>()
    var resultshowmaxprogressfinal = MutableLiveData<String>()
    var resultshowCompressionDistancefinal = MutableLiveData<String>()
    var resultshowpercentprogressfinal = MutableLiveData<String>()
    var notify = MutableLiveData<String>()
    fun result() {
        CompressionDistancefinal.value = (EjectionStroke.value!!.toDouble() + SpringLength.value!!.toDouble() - SpringHousing.value!!.toDouble()).toString()
        maxprogressfinal.value = (SpringNoOfSpace.value!!.toDouble() * SpringSpace.value!!.toDouble()).toString()
        percentprogressfinal.value =
            ((EjectionStroke.value!!.toDouble() + SpringLength.value!!.toDouble() - SpringHousing.value!!.toDouble()) / SpringLength.value!!.toDouble() * 100).toString()
    }

    init {
        EjectionStroke.value = ""
        SpringLength.value = ""
        SpringHousing.value = ""
        SpringSpace.value = ""
        SpringNoOfSpace.value = ""
    }
}