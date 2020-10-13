package com.apptikar.springer.login

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
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.common.MyApplication
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.ActivityLoginBinding
import com.apptikar.springer.proccessactivites.FragmentHolder
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
        textColor()
        binding.checkBox.setOnCheckedChangeListener { buttonView: CompoundButton, isChecked: Boolean ->
            val prefs = getSharedPreferences("remember", MODE_PRIVATE)
            val editor = prefs.edit()
            if (buttonView.isChecked) {
                editor.putBoolean("rememberMe", true)
                editor.apply()
            } else if (!buttonView.isChecked) {
                editor.putBoolean("rememberMe", false)
                editor.apply()
            }
        }
    }

    private fun textColor() {
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
            if (!TextUtils.isEmpty(loginViewModel!!.emailAddress.value) && !TextUtils.isEmpty(loginViewModel!!.pass.value)) {
                    mAuth!!.signInWithEmailAndPassword(loginViewModel!!.emailAddress.value!!, loginViewModel!!.pass.value!!).addOnCompleteListener(this) { task: Task<AuthResult?> ->
                        if (task.isSuccessful) {
                            if (mAuth!!.currentUser!!.isEmailVerified) {
                            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                            intent(this, FragmentHolder::class.java, true)
                                }else
                            {
                                longToastError("please verify your email")
                            }
                        } else {
                            longToastError(task.exception!!.message)
                        }
                    }
                }

        } else {
            longToastError("No Internet Connection")
        }
    }

    fun googleSignIn(view: View) {
        baseGoogleSignIn()
    }

    fun faceSignIn(view: View) {
        baseSigInWithFacebook()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.forgot -> {
                intent(this, Resetpass::class.java, false)

            }
            R.id.loginlayout -> {
                login()

            }
            R.id.signuptextview -> intent(this, SignUp::class.java, false)
        }
    }
}