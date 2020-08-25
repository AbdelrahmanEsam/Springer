package com.first.springer.login
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.databinding.DataBindingUtil
import com.first.springer.R
import com.first.springer.common.BaseActivity
import com.first.springer.databinding.ActivityLoginandsignBinding
import com.google.firebase.auth.FirebaseAuth

class Loginandsign : BaseActivity() {
    private lateinit  var binding: ActivityLoginandsignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_loginandsign)
        mAuth = FirebaseAuth.getInstance()
        textcolor()
    }

    fun googlesignin(view: View) {
        Basegooglesignin()
    }

    fun facesignin(view: View) {
        BaseSiginWithFacebook()
    }

    private fun textcolor() {
        val word: Spannable = SpannableString("Welcome To ")
        word.setSpan(ForegroundColorSpan(Color.rgb(45, 44, 46)), 0, word.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView.text = word
        val wordtwo: Spannable = SpannableString("Springer ")
        wordtwo.setSpan(ForegroundColorSpan(Color.rgb(0, 145, 142)), 0, wordtwo.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView.append(wordtwo)
        val wordthree: Spannable = SpannableString("By counting you agree to the ")
        wordthree.setSpan(ForegroundColorSpan(Color.rgb(0, 0, 0)), 0, wordthree.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView3.text = wordthree
        val wordfour: Spannable = SpannableString("Terms & Privacy Policy")
        wordfour.setSpan(ForegroundColorSpan(Color.rgb(0, 145, 142)), 0, wordfour.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.textView3.append(wordfour)
    }

    fun login(view: View) {
        Intent(this, Login::class.java, false)
    }

    fun signup(view: View) {
        Intent(this, SignUp::class.java, false)
    }
}