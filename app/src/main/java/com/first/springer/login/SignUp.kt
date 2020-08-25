package com.first.springer.login
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.first.springer.R
import com.first.springer.common.BaseActivity
import com.first.springer.common.MyApplication
import com.first.springer.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.regex.Pattern

class SignUp : BaseActivity() {
   private lateinit var signupViewModel: SignupViewModel
   private lateinit  var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signupViewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.viewModel = signupViewModel
        binding.lifecycleOwner = this
    }

    fun googlesignin(view: View) {
        Basegooglesignin()
    }

    fun facesignin(view: View) {
        BaseSiginWithFacebook()
    }

    init {
        if (MyApplication.available) {
            val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
            val pattern = Pattern.compile(PASSWORD_PATTERN)
            if (!TextUtils.isEmpty(signupViewModel.emailAddress.value) && !TextUtils.isEmpty(signupViewModel.pass.value) && !TextUtils.isEmpty(signupViewModel.name.value) &&
                    signupViewModel.name.value!!.length >= 6 && Patterns.EMAIL_ADDRESS.matcher(signupViewModel.emailAddress.value as CharSequence).matches())
            {
                binding.Emailedit.error = null
                binding.password.error = null
                binding.Nameedit.error = null
                if (signupViewModel.pass.value!!.length >= 8 && pattern.matcher(signupViewModel.pass.value as CharSequence).matches())
                {
                    mAuth!!.createUserWithEmailAndPassword(signupViewModel.emailAddress.value!!,signupViewModel.pass.value!!)
                        .addOnCompleteListener(this) { task: Task<AuthResult?> ->
                            when {
                                task.isSuccessful -> {
                                    mAuth!!.currentUser!!.sendEmailVerification()
                                    val intent = Intent(this@SignUp, Login::class.java)
                                    startActivity(intent)
                                    finish()
                                    Longtoastsuccess("check your Email for verification")
                                }
                                task.exception is FirebaseAuthUserCollisionException -> {
                                    Longtoasterror("Email is already exist please sign in")
                                }
                                else -> {
                                    Longtoasterror("Authentication failed")
                                }
                            }
                        }
                } else {
                    if (signupViewModel.pass.value!!.length < 8) {
                        binding.password.error = "password must be more than 8 letters"
                    } else {
                        binding.password.error = ""
                        Longtoasterror("password must have at least one Capital letter , one small letter and one number")
                    }
                }
            } else {
                if (TextUtils.isEmpty(signupViewModel.emailAddress.value)) {
                    binding.Emailedit.error = "required"
                } else if (!Patterns.EMAIL_ADDRESS.matcher(signupViewModel.emailAddress.value as CharSequence) .matches()
                ) {
                    binding.Emailedit.error = "this doesn't match an email address"
                }
                if (TextUtils.isEmpty(signupViewModel.pass.value)) {
                    binding.password.error = "required"
                }
                if (TextUtils.isEmpty(signupViewModel.name.value)) {
                    binding.Nameedit.error = "required"
                } else if (signupViewModel.name.value!!.length < 6) {
                    Nameedit.error = "Name must be more than 5 letters"
                }
            }
        } else {
            Longtoasterror("No internet connection")
        }
    }
}