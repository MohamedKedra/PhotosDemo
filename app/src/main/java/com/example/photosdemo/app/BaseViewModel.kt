package com.example.photosdemo.app

import android.net.ConnectivityManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseViewModel(private val connectivityManager: ConnectivityManager) {

    var requestInProgress = false
        private set

    fun <T> performApiCall(liveData: LiveDataState<T>, call: Call<T>) {
        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                response.body()?.let {
                    publishResult(liveData, it)
                } ?: run {
                    publishError(liveData, Throwable())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                publishError(liveData, t)
            }
        })
    }

    protected val isNetworkAvailable: Boolean
        get() {
            val activeNetInfo = connectivityManager.activeNetworkInfo
            return if (activeNetInfo != null) {
                activeNetInfo.isAvailable && activeNetInfo.isConnected
            } else {
                false
            }
        }

    fun <T> publishLoading(liveData: LiveDataState<T>) {
        requestInProgress = true
        liveData.postLoading()
    }

    fun <T> publishNoInternet(liveData: LiveDataState<T>) {
        requestInProgress = false
        liveData.postNoInternet()
    }

    fun <T> publishError(liveData: LiveDataState<T>, t: Throwable) {
        requestInProgress = false
        liveData.postError(t)
    }

    fun <T> publishResult(liveData: LiveDataState<T>, data: T) {
        requestInProgress = false
        liveData.postSuccess(data)
    }
}