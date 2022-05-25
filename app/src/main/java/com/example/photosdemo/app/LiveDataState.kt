package com.example.photosdemo.app

import androidx.lifecycle.MutableLiveData

class LiveDataState<T> : MutableLiveData<DataState<T>>() {

    fun postLoading(){
        postValue(DataState<T>().getLoading())
    }

    fun postSuccess(data : T){
        postValue(DataState<T>().getSuccess(data))
    }

    fun postError(t : Throwable) {
        postValue(DataState<T>().getError(t))
    }

    fun postNoInternet() {
        postValue(DataState<T>().getNoInternet())
    }
}

class DataState<T> {

    private var dataStatus : DataStatus = DataStatus.LOADING

    private var data : T? = null

    private var error : Throwable? = null

    fun getLoading() : DataState<T> {

        this.dataStatus = DataStatus.LOADING
        this.data = null
        this.error = null
        return this
    }

    fun getSuccess(data : T) : DataState<T> {

        this.dataStatus = DataStatus.SUCCESS
        this.data = data
        this.error = null
        return this
    }

    fun getError(throwable: Throwable) : DataState<T> {

        this.dataStatus = DataStatus.ERROR
        this.data = null
        this.error = throwable
        return this
    }

    fun getNoInternet(): DataState<T> {

        this.dataStatus = DataStatus.NO_INTERNET
        return this
    }

    fun getStatus() : DataStatus = this.dataStatus

    fun getData() : T? = this.data

    fun getError() : Throwable? = this.error

    enum class DataStatus{
        LOADING,
        SUCCESS,
        ERROR,
        NO_INTERNET,
    }
}