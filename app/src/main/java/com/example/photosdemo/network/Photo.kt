package com.example.photosdemo.network

data class Photo(
    val albumId: Int? = 0,
    val id: Int? = 0,
    val thumbnailUrl: String? = "",
    val title: String? = "",
    val url: String? = ""
)