package com.apptikar.springer.login
import android.os.Bundle
import android.util.Patterns
import androidx.databinding.DataBindingUtil
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.ActivityResetpassBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult


class Resetpass : BaseActivity() {
   private lateinit var binding: ActivityResetpassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_resetpass)
        binding.reset.setOnClickListener {
            if (available) {
                val emailToReset = binding.resetedittext.editText!!.text.toString().trim { it <=' '}
                if (emailToReset.isNotEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(emailToReset).matches()) {
                        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(emailToReset).addOnCompleteListener { task: Task<SignInMethodQueryResult> ->
                                val isNewUser = task.result!!.signInMethods!!.isEmpty()
                                val signInMethods = task.result!!.signInMethods
                                if (isNewUser) {
                                    longToastError("this Email doesn't exist")
                                } else {
                                        when {
                                            signInMethods!![0] == "google.com" -> {
                                                longToastError("your Email is provided by google")
                                            }
                                            signInMethods[0] == "facebook.com" -> {
                                                longToastError("your Email is provided by facebook")
                                            }
                                            else -> {
                                                FirebaseAuth.getInstance().sendPasswordResetEmail(emailToReset).addOnCompleteListener { task1: Task<Void?> ->
                                                    if (task1.isSuccessful) {
                                                        longToastSuccess("check your Email please")
                                                    } else {
                                                        longToastError("error")
                                                    }
                                                }
                                            }
                                        }
                                }
                            }
                    } else {
                        longToastError("this doesn't match an email address")
                    }
                } else {
                    binding.resetedittext.error = ""
                }
            } else {
                longToastError("You are offline")
            }
        }
    }
}