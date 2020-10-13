package com.apptikar.springer.login
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.common.MyApplication
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.databinding.ActivitySignUpBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
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

    fun googleSignIn(view: View) {
        baseGoogleSignIn()
    }

    fun faceSignIn(view: View) {
        baseSigInWithFacebook()
    }

  fun signUp(view: View) {
      if (MyApplication.available) {
          val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"
          val pattern = Pattern.compile(PASSWORD_PATTERN)
          if (!signupViewModel.emailAddress.value.isNullOrEmpty() && !signupViewModel.pass.value.isNullOrEmpty() && !signupViewModel.name.value.isNullOrEmpty() && signupViewModel.name.value!!.length >= 6 && Patterns.EMAIL_ADDRESS.matcher(
                  signupViewModel.emailAddress.value as CharSequence
              ).matches()) {
              binding.Emailedit.error = null
              binding.password.error = null
              binding.Nameedit.error = null
              if (signupViewModel.pass.value!!.length >= 8 && pattern.matcher(signupViewModel.pass.value as CharSequence).matches()) {
                  mAuth!!.createUserWithEmailAndPassword(
                      signupViewModel.emailAddress.value!!,
                      signupViewModel.pass.value!!).addOnCompleteListener(this) { task: Task<AuthResult?> ->
                          when {
                              task.isSuccessful -> {
                                  val profileUpdates = UserProfileChangeRequest.Builder()
                                      .setDisplayName(signupViewModel.name.value)
                                      .build()
                                  mAuth!!.currentUser!!.updateProfile(profileUpdates)
                                  mAuth!!.currentUser!!.sendEmailVerification()
                                  val intent = Intent(this@SignUp, Login::class.java)
                                  startActivity(intent)
                                  finish()
                                  longToastSuccess("check your Email for verification")
                              }
                              task.exception is FirebaseAuthUserCollisionException -> {
                                  longToastError("Email is already exist please sign in")
                              }
                              else -> {
                                  longToastError("Authentication failed")
                              }
                          }
                      }
              } else {
                  if (signupViewModel.pass.value!!.length < 8) {
                      binding.password.error = "password must be more than 8 letters"
                  } else {
                      binding.password.error = ""
                      longToastError("password must have at least one Capital letter , one small letter and one number")
                  }
              }
          } else {
              if (TextUtils.isEmpty(signupViewModel.emailAddress.value)) {
                  binding.Emailedit.error = "required"
              } else if (!Patterns.EMAIL_ADDRESS.matcher(signupViewModel.emailAddress.value as CharSequence)
                      .matches()
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
          longToastError("No internet connection")
      }
  }
}