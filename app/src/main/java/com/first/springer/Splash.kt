package com.first.springer
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.first.springer.common.BaseActivity
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.login.Loginandsign
import com.first.springer.proccessactivites.Fragmentholder
import com.first.springer.tutorial.Tutorial
import com.google.firebase.auth.FirebaseAuth
import  kotlinx.android.synthetic.main.activity_splash.*

class Splash : BaseActivity() {
    var first = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        motion.transitionToEnd()
        val firstsharedprefrence = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        first = firstsharedprefrence.getBoolean("first", true)
        mAuth = FirebaseAuth.getInstance()
        Handler().postDelayed({
            if (first) {
                Intent(this, Tutorial::class.java, true)
                val editor = firstsharedprefrence.edit()
                editor.putBoolean("first", false)
                editor.apply()
            } else {
                if (available) {
                    val remember= getSharedPreferences("remember", Context.MODE_PRIVATE)
                    val rememberme = remember.getBoolean("rememberme", false)
                    if (rememberme) {
                        if (mAuth.currentUser != null) {
                            Intent(this, Fragmentholder::class.java, true)
                        } else {
                            Intent(this, Loginandsign::class.java, true)
                        }
                    } else {
                        Intent(this, Loginandsign::class.java, true)
                    }
                } else {
                    Intent(this, Loginandsign::class.java, true)
                }
            }
        }, 3000)
    }
}