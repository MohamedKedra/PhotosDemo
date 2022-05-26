package com.example.photosdemo

import android.content.Context
import com.example.photosdemo.app.Dependences

class Utils {

    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val activeNetInfo = Dependences.getConnectivity(context).activeNetworkInfo
            return if (activeNetInfo != null) {
                activeNetInfo.isAvailable && activeNetInfo.isConnected
            } else {
                false
            }
        }
    }
}