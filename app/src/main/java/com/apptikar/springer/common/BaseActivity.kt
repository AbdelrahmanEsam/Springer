package com.apptikar.springer.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.apptikar.springer.R
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.proccessactivites.FragmentHolder
import com.apptikar.springer.proccessactivites.ProcessViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {
  val viewModel: ProcessViewModel by viewModels()
    private var face = false
    private var callbackManager: CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.notify.value = "no"
        val monitor = ConnectionStateMonitor()
        monitor.enable(this)
    }

    protected fun baseGoogleSignIn() {
        if (available) {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.webclient))
                .requestScopes(Scope("https://www.googleapis.com/auth/profile.agerange.read"))
                .requestEmail()
                .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, 100)
        } else {
            longToastError("No internet connection")
        }
    }

    protected fun glide(load: String?, imageView: ImageView?) {
        Glide.with(this)
            .load(load)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView!!)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct!!.idToken, null)
      mAuth?.signInWithCredential(credential)?.addOnCompleteListener(this) { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    val intent = Intent(this, FragmentHolder::class.java)
                    startActivity(intent)
                    finish()
                    val prefs = getSharedPreferences("remember", MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putBoolean("rememberMe", true)
                    editor.apply()
                } else {
                    longToastError("Authentication failed")
                }
            }
    }

    protected fun baseSigInWithFacebook() {
        face = true
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(
            this,
            listOf("email")
        )
        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                    )
                    val accessToken = loginResult.accessToken
                    val credential = FacebookAuthProvider.getCredential(accessToken.token)
                    mAuth?.signInWithCredential(credential)
                        ?.addOnCompleteListener { task: Task<AuthResult?> ->
                            if (task.isSuccessful) {
                                val prefs = getSharedPreferences("remember", MODE_PRIVATE)
                                val editor = prefs.edit()
                                editor.putBoolean("rememberMe", true)
                                editor.apply()
                                val intent = Intent(this@BaseActivity, FragmentHolder::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                                longToastError(task.exception!!.message)
                            }
                        }
                }

                override fun onCancel() {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                }

                override fun onError(exception: FacebookException) {
                    longToastError("No internet connection")
                }
            })
    }

    protected fun shortToast(message: String?) {
        FancyToast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun longToast(message: String?) {
        FancyToast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    protected fun longToastError(message: String?) {
        FancyToast.makeText(this, message, Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
    }

    protected fun longToastSuccess(message: String?) {
        FancyToast.makeText(this, message, Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }

    protected fun intent(context1: Context?, class2: Class<*>?, finish: Boolean) {
        val intent = Intent(context1, class2)
        startActivity(intent)
        if (finish) {
            finish()
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 100) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                longToastError(e.message)
            }
        } else if (requestCode == 500 && resultCode == RESULT_OK) {
            viewModel.resultUri.setValue(data!!.data.toString())
        } else if (face) {
            callbackManager!!.onActivityResult(requestCode, resultCode, data)
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            if (data != null) {
                data.data
                viewModel.editUri.value = data.data.toString()
                        val image =  findViewById<ImageView>(R.id.selectimage)
                        glide(data.data!!.toString(),image)


                }


            }
        }
    }
