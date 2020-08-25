package com.first.springer.login

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.WindowManager
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.first.springer.R
import com.first.springer.common.BaseActivity
import com.first.springer.common.MyApplication
import com.first.springer.databinding.ActivityLoginBinding
import com.first.springer.proccessactivites.Fragmentholder
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class Login : BaseActivity(), View.OnClickListener {
    private var loginViewModel: LoginViewModel? = null
    private lateinit  var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.viewModel = loginViewModel
        binding.lifecycleOwner = this
        binding.loginlayout.setOnClickListener(this)
        binding.forgot.setOnClickListener(this)
        binding.signuptextview.setOnClickListener(this)
        textcolor()
        binding.checkBox.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
            val prefs = getSharedPreferences("remember", MODE_PRIVATE)
            val editor = prefs.edit()
            if (buttonView.isChecked) {
                editor.putBoolean("rememberme", true)
                editor.apply()
            } else if (!buttonView.isChecked) {
                editor.putBoolean("rememberme", false)
                editor.apply()
            }
        }
    }

    private fun textcolor() {
        val wordthree: Spannable = SpannableString("Don't have an account ? ")
        wordthree.setSpan(
            ForegroundColorSpan(Color.rgb(192, 192, 192)),
            0,
            wordthree.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signuptextview.text = wordthree
        val wordfour: Spannable = SpannableString("Sign Up")
        wordfour.setSpan(
            ForegroundColorSpan(Color.rgb(0, 0, 0)),
            0,
            wordfour.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.signuptextview.append(wordfour)
    }

    private fun login() {
        if (MyApplication.available) {
            if (!TextUtils.isEmpty(loginViewModel!!.emailAddress.value) && !TextUtils.isEmpty(
                    loginViewModel!!.pass.value
                )
            ) {
                mAuth!!.signInWithEmailAndPassword(
                    loginViewModel!!.emailAddress.value!!, loginViewModel!!.pass.value!!
                ).addOnCompleteListener(this) { task: Task<AuthResult?> ->
                        if (task.isSuccessful) {
                            window.setFlags(
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                            )
                            Intent(this, Fragmentholder::class.java, true)
                        } else {
                            Longtoasterror(task.exception!!.message)
                        }
                    }
            }
        } else {
            Longtoasterror("No Internet Connection")
        }
    }

    fun googlesignin(view: View) {
        Basegooglesignin()
    }

    fun facesignin(view: View) {
        BaseSiginWithFacebook()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.forgot -> {
                Intent(this, Resetpass::class.java, false)
                login()
                Intent(this, SignUp::class.java, false)
            }
            R.id.loginlayout -> {
                login()
                Intent(this, SignUp::class.java, false)
            }
            R.id.signuptextview -> Intent(this, SignUp::class.java, false)
        }
    }
}