package com.macary.hnmobile.utils.networkMonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

/**
 * Created by Carlos Farfan on 6/01/2021.
 */
class ConnectionStateMonitor(private val context: Context) : ConnectivityManager.NetworkCallback() {

    private lateinit var connectivityManager: ConnectivityManager
    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private lateinit var listener: (Boolean) -> Unit

    fun enable(f: (Boolean) -> Unit) {
        listener = f
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    fun disable() {
        connectivityManager.unregisterNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener(true)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        listener(false)
    }
}