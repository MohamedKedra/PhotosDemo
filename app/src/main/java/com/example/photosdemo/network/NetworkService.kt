package com.example.photosdemo.network

import retrofit2.Call
import retrofit2.http.GET

interface NetworkService {

    @GET("photos")
    fun getAllPhotos(): Call<List<Photo>>
}