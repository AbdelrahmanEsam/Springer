package com.apptikar.springer
import android.content.Context
import android.os.Bundle
import android.os.Handler
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.common.MyApplication.Companion.available
import com.apptikar.springer.common.MyApplication.Companion.mAuth
import com.apptikar.springer.login.Loginandsign
import com.apptikar.springer.proccessactivites.FragmentHolder
import com.apptikar.springer.tutorial.Tutorial
import  kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : BaseActivity() {
    var first = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        motion.transitionToEnd()
        val firstSharedPreference = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        first = firstSharedPreference.getBoolean("first", true)
        CoroutineScope(Main).launch {
            delay(3000)
            if (first) {
                intent(this@Splash, Tutorial::class.java, true)
                val editor = firstSharedPreference.edit()
                editor.putBoolean("first", false)
                editor.apply()
            } else {
                if (available) {
                    val remember= getSharedPreferences("remember", Context.MODE_PRIVATE)
                    val rememberMe = remember.getBoolean("rememberMe", false)
                    if (rememberMe) {

                        if (mAuth!!.currentUser != null) {
                            intent(this@Splash, FragmentHolder::class.java, true)
                        } else {
                            intent(this@Splash, Loginandsign::class.java, true)
                        }
                    } else {
                        intent(this@Splash, Loginandsign::class.java, true)
                    }
                } else {
                    intent(this@Splash, Loginandsign::class.java, true)
                }
            }

        }
    }
}