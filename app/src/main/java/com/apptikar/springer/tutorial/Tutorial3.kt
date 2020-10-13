package com.apptikar.springer.tutorial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.apptikar.springer.R
import com.apptikar.springer.login.Loginandsign

/**
 * A simple [Fragment] subclass.
 */
class Tutorial3 : Fragment() {
  private  lateinit var getstarted: AppCompatButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tutorial3, container, false)
        getstarted = view.findViewById(R.id.getstarted)
        getstarted.setOnClickListener {
            val intent = Intent(context, Loginandsign::class.java)
            startActivity(intent)
            activity!!.finish()
        }
        return view
    }
}