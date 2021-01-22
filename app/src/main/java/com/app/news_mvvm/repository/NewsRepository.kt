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

    suspend fun fetchSourcesFromApi(countryCode: String) :Response<Sources> {
        return RetrofitUtils.buildApiService.fetchSources(countryCode)
    }
    suspend fun addSourceToDatabase(sourceList :List<SourceSelect>) {
        sourceDao.insertSourceList(sourceList)
    }

    fun getSourceFromDatabase() = sourceDao.getAllSources()

    suspend fun fetchHeadlinesFromServer(countryCode: String) =
            RetrofitUtils.buildApiService.fetchTopHeadlines(countryCode)

    suspend fun fetchSourceNewsFromServer(sourceCode :String) =
            RetrofitUtils.buildApiService.fetchSourceNews(sourceCode)

    suspend fun fetchSearchedNews(query :String) =
        RetrofitUtils.buildApiService.searchNews(query , 1)



    companion object {
        private var INSTANCE: NewsRepository? = null

        fun getRepoInstance(context: Context): NewsRepository =
            INSTANCE ?: NewsRepository(context).also {
                INSTANCE = it
            }
    }
}