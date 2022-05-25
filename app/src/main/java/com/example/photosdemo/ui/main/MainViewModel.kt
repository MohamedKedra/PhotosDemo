package com.example.photosdemo.ui.main

import android.net.ConnectivityManager
import com.example.photosdemo.app.BaseViewModel
import com.example.photosdemo.app.LiveDataState
import com.example.photosdemo.network.Photo
import com.example.photosdemo.repository.MainRepository

class MainViewModel(
    private val repository: MainRepository,
    connectivityManager: ConnectivityManager
) : BaseViewModel(connectivityManager) {

    private var dataList = LiveDataState<List<Photo>>()

    fun refreshMain(): LiveDataState<List<Photo>> {

        if (!isNetworkAvailable) {
            publishNoInternet(dataList)
            return dataList
        }

        publishLoading(dataList)

        performApiCall(dataList, repository.getAllPhotos())

        return dataList
    }


}

