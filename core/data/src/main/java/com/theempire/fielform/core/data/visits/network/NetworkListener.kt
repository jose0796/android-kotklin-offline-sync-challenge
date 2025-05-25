package com.theempire.fielform.core.data.visits.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import com.theempire.fielform.domain.visits.network.NetworkListener
import com.theempire.fielform.domain.visits.sync.UploadManager
import javax.inject.Inject

class NetworkListenerImpl @Inject constructor(
    private val context: Context,
    private val uploadManager: UploadManager
) : NetworkListener {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun startListening(
        onNetworkLost: () -> Unit,
        onNetworkRestored: () -> Unit
    ) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onNetworkRestored()
                uploadManager.scheduleSync()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onNetworkLost()
            }
        })
    }
}