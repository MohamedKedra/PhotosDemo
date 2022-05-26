package com.example.photosdemo.app

import android.content.Context
import android.net.ConnectivityManager
import com.example.photosdemo.network.NetworkApi
import com.example.photosdemo.network.NetworkService
import com.example.photosdemo.repository.MainRepository
import com.example.photosdemo.ui.main.MainViewModel

class Dependences {

    companion object {

        fun getViewModel(context: Context): MainViewModel {
            return MainViewModel(getRepository(context), getConnectivity(context))
        }

        private fun getRepository(context: Context): MainRepository {
            return MainRepository(getService(context))
        }

        private fun getService(context: Context): NetworkService {

            return NetworkApi.getClient(context).create(NetworkService::class.java)
        }

        fun getConnectivity(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }
    }
}