package com.theempire.fielform.domain.visits.network

interface NetworkListener {
    fun startListening(onNetworkLost: () -> Unit = {}, onNetworkRestored: () -> Unit = {})
}