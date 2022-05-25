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
            return MainViewModel(getRepository(), getConnectivity(context))
        }

        private fun getRepository(): MainRepository {
            return MainRepository(getService())
        }

        private fun getService(): NetworkService {

            return NetworkApi.getClient().create(NetworkService::class.java)
        }

        private fun getConnectivity(context: Context): ConnectivityManager {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

    }
}