package com.first.springer.common
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast
import com.first.springer.common.MyApplication.Companion.available
import com.first.springer.common.MyApplication.Companion.wasInBackground
import com.shashank.sony.fancytoastlib.FancyToast
import javax.inject.Inject

class ConnectionStateMonitor @Inject constructor() : NetworkCallback() {
    var show = false
    private lateinit  var context : Context
   private val networkRequest = NetworkRequest.Builder()
       .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
       .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
       .build()

    fun enable(context: Context) {
        this.context = context
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    override fun onAvailable(network: Network) {
     available = true
        if (wasInBackground) {
            if (show) {
                FancyToast.makeText(context, "you are online", Toast.LENGTH_LONG, FancyToast.SUCCESS, false).show()
            }
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        available = false
        if (wasInBackground) {
            FancyToast.makeText(context, "you are offline", Toast.LENGTH_LONG, FancyToast.ERROR, false).show()
            show = true
        }
    }

}