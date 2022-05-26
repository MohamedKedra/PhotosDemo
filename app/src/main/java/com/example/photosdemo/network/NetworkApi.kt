package com.example.photosdemo.network

import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object NetworkApi {

    fun getClient(context: Context): Retrofit {

        return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient(context))
            .build()
    }
}

fun getHttpClient(context: Context): OkHttpClient {
    val interceptor = Interceptor { chain ->
        val res: Response = chain.proceed(chain.request())
        res.newBuilder()
            .header(APICaching.headerCacheControl, APICaching.offlineCache.plus(60 * 60 * 24 * 30))
            .removeHeader(APICaching.headerPargma)
            .build()
    }

    val onlineInterceptor = Interceptor { chain ->
        val res: Response = chain.proceed(chain.request())
        res.newBuilder()
            .header(APICaching.headerCacheControl, APICaching.headerOnline.plus(60))
            .removeHeader(APICaching.headerPargma)
            .build()
    }

    val cache = Cache(File(context.cacheDir, "responses"), 10 * 1024 * 1024)
    return OkHttpClient().newBuilder()
        .addInterceptor(interceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .cache(cache)
        .build()
}

object APICaching {
    const val headerCacheControl = "Cache-Control"
    const val headerPargma = "Pragma"
    const val headerOnline = "public, max-age="
    const val offlineCache = "public, only-if-cached, max-stale="
}
