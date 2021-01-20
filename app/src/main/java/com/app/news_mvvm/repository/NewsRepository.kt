package com.app.news_mvvm.repository

import android.content.Context
import android.util.Log
import com.app.news_mvvm.interfaces.OnNewsCallback
import com.app.news_mvvm.interfaces.OnSourceCallback
import com.app.news_mvvm.model.News
import com.app.news_mvvm.model.SourceSelect
import com.app.news_mvvm.model.Sources
import com.app.news_mvvm.network.RetrofitUtils
import com.app.news_mvvm.room.AppDatabase
import com.app.news_mvvm.room.SourceDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "NewsRepositoryClass"
class NewsRepository(context: Context) {

    private var sourceDao :SourceDao
    init {
        Log.d(TAG, "NewsRepo init : ${hashCode()} ")
        val db = AppDatabase(context)
        sourceDao = db.sourceDao()
    }
   /* fun fetchSourcesFromApi(countryCode :String, onSourceCallback: OnSourceCallback) {
        RetrofitUtils.buildApiService.fetchSources(countryCode)
                .enqueue(object : Callback<Sources> {
                    override fun onResponse(call: Call<Sources>, response: Response<Sources>) {
                        if (response.isSuccessful) {
                            response.body()?.sources?.let {
                                onSourceCallback.onSuccessResponse(it)
                            }
                        } else {
                            onSourceCallback.onErrorResponse("Something Went Wrong")
                        }
                    }

                    override fun onFailure(call: Call<Sources>, t: Throwable) {
                        onSourceCallback.onErrorResponse("Something Went Wrong :${t.localizedMessage}")
                        Log.d(TAG, "onFailure: ${t.localizedMessage}")
                    }
                })
    }*/

    suspend fun fetchSourcesFromApi(countryCode: String) :Response<Sources> {
        return RetrofitUtils.buildApiService.fetchSources(countryCode)
    }
    suspend fun addSourceToDatabase(sourceList :List<SourceSelect>) {
        sourceDao.insertSourceList(sourceList)
    }

    fun getSourceFromDatabase() = sourceDao.getAllSources()

    fun fetchHeadlinesFromServer(countryCode: String, newsCallback: OnNewsCallback) {
        RetrofitUtils.buildApiService.fetchTopHeadlines(countryCode)
            .enqueue(object : Callback<News> {

                override fun onResponse(call: Call<News>, response: Response<News>) {
                    if (response.isSuccessful) {

                        try {
                            newsCallback.onSuccessResponse(response.body()!!)
                        } catch (e: NullPointerException) {
                            newsCallback.onErrorResponse("Something Went Wrong , try again later")
                        }
                    }
                    else {
                        newsCallback.onErrorResponse("Something Went Wrong , ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.d(TAG, "onFailure: ${t.localizedMessage}")
                    newsCallback.onErrorResponse("Something Went Wrong , ${t.localizedMessage}")
                }
            })
    }

    fun fetchSourceNewsFromServer(sourceCode :String , newsCallback: OnNewsCallback) {
        RetrofitUtils.buildApiService.fetchSourceNews(sourceCode)
                .enqueue(object : Callback<News> {
                    override fun onResponse(call: Call<News>, response: Response<News>) {
                        if (response.isSuccessful) {
                            try {
                                newsCallback.onSuccessResponse(response.body()!!)
                            } catch (e: java.lang.NullPointerException) {
                                newsCallback.onErrorResponse("Something Went Wrong , try again later")
                            }

                        } else {
                            newsCallback.onErrorResponse("Something Went Wrong , ${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<News>, t: Throwable) {
                        Log.d(TAG, "onFailure: ${t.localizedMessage}")
                        newsCallback.onErrorResponse("Something Went Wrong , ${t.localizedMessage}")
                    }
                })
    }

    companion object {
        private var INSTANCE: NewsRepository? = null

        fun getRepoInstance(context: Context): NewsRepository =
            INSTANCE ?: NewsRepository(context).also {
                INSTANCE = it
            }
    }
}