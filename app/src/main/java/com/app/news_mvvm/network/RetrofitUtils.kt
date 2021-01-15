package com.app.news_mvvm.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils  {
    private const val BASE_URL = "https://newsapi.org/v2/"
    private const val API_KEY = "000000000000000000000000000"


    private val logIntercept :HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    private val okHttpClient :OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor {
            val request = it.request()
            val url = request.url
            val mUrl = url.newBuilder().addQueryParameter("apiKey" , API_KEY ).build()
            val reqBuilder = request.newBuilder().url(mUrl)
            it.proceed(reqBuilder.build())
        }.apply { addInterceptor(logIntercept) }.build()
    }

    private val getRetrofit :Retrofit by lazy {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    val buildApiService :NewsApiService by lazy {
        getRetrofit.create(NewsApiService ::class.java)
    }
}