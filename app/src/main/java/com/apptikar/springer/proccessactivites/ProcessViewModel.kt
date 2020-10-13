package com.apptikar.springer.proccessactivites
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apptikar.springer.ModelResults
import kotlin.math.round

class ProcessViewModel : ViewModel() {

    var moldOrDie = MutableLiveData<String>()
    var moldOrDieName = MutableLiveData<String>()
    var moldOrDieCode = MutableLiveData<String>()
    var color = MutableLiveData<String>()
    var springLength = MutableLiveData<String>()
    var springSpace = MutableLiveData<String>()
    var springNoOfSpace = MutableLiveData<String>()
    var springHousing = MutableLiveData<String>()
    var ejectionStroke = MutableLiveData<String>()
    var editUri = MutableLiveData<String>()
    var editName = MutableLiveData<String>()
    var editEmail = MutableLiveData<String>()
    var editAge = MutableLiveData<String>()
    var editGender = MutableLiveData<String>()
    var editPassword = MutableLiveData<String>()
    var percentProgressFinal = MutableLiveData<String>()
    var maxProgressFinal = MutableLiveData<String>()
    var compressionDistanceFinal = MutableLiveData<String>()
    var resultUri = MutableLiveData<String>()
    var resultShowMaxProgressFinal = MutableLiveData<String>()
    var resultShowCompressionDistanceFinal = MutableLiveData<String>()
    var resultShowPercentProgressFinal = MutableLiveData<String>()
    var notify = MutableLiveData<String>()
    var modelResults = MutableLiveData<ModelResults>()
    fun result() {
        compressionDistanceFinal.value = (ejectionStroke.value!!.toDouble() + springLength.value!!.toDouble() - springHousing.value!!.toDouble()).toString()
        maxProgressFinal.value = (springNoOfSpace.value!!.toDouble() * springSpace.value!!.toDouble()).toString()
        percentProgressFinal.value = (round(((ejectionStroke.value!!.toDouble() + springLength.value!!.toDouble() - springHousing.value!!.toDouble()) / springLength.value!!.toDouble() * 100) *100.0)/100.0).toString()
    }


    fun init()
    {
        ejectionStroke.value = ""
        springLength.value = ""
        springHousing.value = ""
        springSpace.value = ""
        springNoOfSpace.value = ""
        moldOrDieName.value = ""
        moldOrDieCode.value = ""
        color.value = ""
    }
    init {
        init()
    }

}