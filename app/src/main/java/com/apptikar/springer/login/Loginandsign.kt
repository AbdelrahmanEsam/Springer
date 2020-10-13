package com.apptikar.springer.login
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.databinding.DataBindingUtil
import com.apptikar.springer.R
import com.apptikar.springer.common.BaseActivity
import com.apptikar.springer.databinding.ActivityLoginandsignBinding

class Loginandsign : BaseActivity() {
    private lateinit  var binding: ActivityLoginandsignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loginandsign)
        textcolor()
    }

    fun googleSignIn(view: View) {
        baseGoogleSignIn()
    }

    fun faceSignIn(view: View) {
        baseSigInWithFacebook()
    }

    private fun textcolor() {
        val word: Spannable = SpannableString("Welcome To ")
        word.setSpan(ForegroundColorSpan(Color.rgb(45, 44, 46)), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView.text = word
        val wordTwo: Spannable = SpannableString("Springer ")
        wordTwo.setSpan(ForegroundColorSpan(Color.rgb(0, 145, 142)), 0, wordTwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView.append(wordTwo)
        val wordThree: Spannable = SpannableString("By counting you agree to the ")
        wordThree.setSpan(ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, wordThree.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView3.text = wordThree
        val wordFour: Spannable = SpannableString("Terms & Privacy Policy")
        wordFour.setSpan(ForegroundColorSpan(Color.rgb(0, 145, 142)), 0, wordFour.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView3.append(wordFour)
    }

    fun login(view: View) {
        intent(this, Login::class.java, false)
    }

    fun signup(view: View) {
        intent(this, SignUp::class.java, false)
    }
}