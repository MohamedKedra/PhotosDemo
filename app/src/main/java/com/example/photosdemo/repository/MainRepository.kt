package com.example.photosdemo.repository

import com.example.photosdemo.network.NetworkService
import com.example.photosdemo.network.Photo
import retrofit2.Call

class MainRepository(private val networkService: NetworkService) {

    fun getAllPhotos(): Call<List<Photo>> = networkService.getAllPhotos()
}