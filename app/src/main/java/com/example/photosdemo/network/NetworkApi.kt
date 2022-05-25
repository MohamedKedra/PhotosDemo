package com.example.photosdemo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkApi {

    fun getClient(): Retrofit {

        return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}