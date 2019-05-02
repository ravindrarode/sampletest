package com.iserve.retrofitdemo.net

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.iserve.retrofitdemo.utility.AppLogs


class ConnectionChecker {
    internal var connected = false
    internal lateinit var connectivityManager: ConnectivityManager
    internal var mobileInfo: NetworkInfo? = null
    internal var wifiInfo: NetworkInfo? = null

    fun isConnected(paramContext: Context): Boolean {

        try {
            this.connectivityManager = paramContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            this.mobileInfo = this.connectivityManager.activeNetworkInfo
            if (this.mobileInfo != null && this.mobileInfo!!.isAvailable && this.mobileInfo!!.isConnected) {
                this.connected = true
            } else {
                this.connected = false
            }
            return this.connected
        } catch (localException: Exception) {
            println("CheckConnectivity Exception: " + localException.message)
            AppLogs.v("connectivity", paramContext.toString())
        }

        return this.connected
    }

    companion object {

        private var connectionChecker: ConnectionChecker? = null

        val instance: ConnectionChecker
            get() {
                if (connectionChecker == null) {
                    connectionChecker = ConnectionChecker()
                }
                return connectionChecker as ConnectionChecker
            }
    }
}
