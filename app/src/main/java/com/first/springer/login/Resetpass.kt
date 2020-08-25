package com.first.springer.login
import android.os.Bundle
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import com.first.springer.R
import com.first.springer.common.BaseActivity
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.databinding.ActivityResetpassBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import java.util.*

class Resetpass : BaseActivity() {
   private lateinit var binding: ActivityResetpassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resetpass)
        binding.reset.setOnClickListener {
            if (available) {
                val Emailtoreset =
                    binding.resetedittext.editText!!.text.toString().trim { it <= ' ' }
                if (!Emailtoreset.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(Emailtoreset).matches()) {
                        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(Emailtoreset)
                            .addOnCompleteListener { task: Task<SignInMethodQueryResult> ->
                                val isNewUser = Objects.requireNonNull(
                                    Objects.requireNonNull(task.result)?.signInMethods
                                )?.isEmpty()
                                val signInMethods = task.result!!
                                    .signInMethods
                                if (isNewUser!!) {
                                    Longtoasterror("this Email doesn't exist")
                                } else {
                                    if (signInMethods!![0] == "google.com") {
                                        Longtoasterror("your Email is provided by google")
                                    } else if (signInMethods[0] == "facebook.com") {
                                        Longtoasterror("your Email is provided by facebook")
                                    } else {
                                        FirebaseAuth.getInstance()
                                            .sendPasswordResetEmail(Emailtoreset)
                                            .addOnCompleteListener { task1: Task<Void?> ->
                                                if (task1.isSuccessful) {
                                                    Longtoastsuccess("check your Email please")
                                                } else {
                                                    Longtoasterror("error")
                                                }
                                            }
                                    }
                                }
                            }
                    } else {
                        Longtoasterror("this doesn't match an email address")
                    }
                } else {
                    binding.resetedittext.error = ""
                }
            } else {
                Longtoasterror("You are offline")
            }
        }
    }
}