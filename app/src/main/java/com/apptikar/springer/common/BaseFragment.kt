package com.apptikar.springer.common

import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.apptikar.springer.room.DatabaseViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class BaseFragment : Fragment() {


    protected   val viewModel: DatabaseViewModel  by viewModels()

    protected fun glide(load: String?, imageView: ImageView?) {
        Glide.with(this)
            .load(load)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(imageView!!)
    }

    protected fun shortToast(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun longToast(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    protected fun longToastError(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
    }

    protected fun longToastSuccess(message: String) {
        FancyToast.makeText(context, message, Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
    }
}