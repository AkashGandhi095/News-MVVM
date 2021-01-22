package com.app.news_mvvm.network

import com.app.news_mvvm.model.News
import com.app.news_mvvm.model.Source
import com.app.news_mvvm.model.Sources
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

  /*  @GET("sources?language=en")
    fun fetchSources(@Query("country") countryCode :String) :Call<Sources>
*/

    @GET("sources?language=en")
    suspend fun fetchSources(@Query("country") countryCode: String) :Response<Sources>

    @GET("top-headlines")
    suspend fun fetchTopHeadlines(@Query("country") countryCode: String) :Response<News>

    @GET("everything")
    suspend fun fetchSourceNews(@Query("sources") source :String) :Response<News>

    @GET("everything")
    suspend fun searchNews(@Query("q") query :String ,
                           @Query("page") page :Int = 1
    ) :Response<News>




}

