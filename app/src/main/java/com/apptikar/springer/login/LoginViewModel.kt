package com.apptikar.springer.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var emailAddress = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
}