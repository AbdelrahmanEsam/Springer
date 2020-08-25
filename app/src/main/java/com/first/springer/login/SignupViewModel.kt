package com.first.springer.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var emailAddress = MutableLiveData<String>()
    var pass = MutableLiveData<String>()
}