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
    fun fetchTopHeadlines(@Query("country") countryCode: String) :Call<News>

    @GET("everything")
    fun fetchSourceNews(@Query("sources") source :String) :Call<News>

}

