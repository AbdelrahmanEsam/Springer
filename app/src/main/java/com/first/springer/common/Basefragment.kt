package com.first.springer.common

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

open class Basefragment : Fragment() {
    @JvmField
    protected var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
    }

    protected fun glide(load: String?, imageView: ImageView?) {
        Glide.with(this)
            .load(load)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView!!)
    }

    protected fun shorttoast(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun Longtoast(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected fun Longtoasterror(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
    }

    protected fun Longtoastsuccess(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }

    protected fun Internetconnection() {}
}