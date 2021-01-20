package com.app.news_mvvm.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.news_mvvm.interfaces.OnNewsCallback
import com.app.news_mvvm.model.Articles
import com.app.news_mvvm.model.News
import com.app.news_mvvm.model.SourceSelect
import com.app.news_mvvm.repository.NewsRepository
import com.app.news_mvvm.utils.SourceResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "NewsViewModel"

class NewsViewModel(application: Application) :AndroidViewModel(application) {

    init {
        Log.d(TAG, "init NewsViewModel :${hashCode()}: ")
    }




   // private val sourceLiveList :MutableLiveData<List<SourceSelect>> by lazy { MutableLiveData() }
    private val sources :MutableLiveData<SourceResource> = MutableLiveData()
    private val newsArticleList :MutableLiveData<List<Articles>> by lazy { MutableLiveData() }
    private val sourceNewsArticlesList :MutableLiveData<List<Articles>> by lazy { MutableLiveData() }
//    private val errorString :MutableLiveData<String> by lazy { MutableLiveData() }

    var countryCode :String = ""

    private fun fetchSourcesFromServer(countryCode: String) {
        viewModelScope.launch {
            sources.value = SourceResource.Loading
            val sourceResponse = NewsRepository
                    .getRepoInstance(getApplication())
                    .fetchSourcesFromApi(countryCode)

            if (sourceResponse.isSuccessful) {
                sourceResponse.body()?.let { response ->
                    //sourceLiveList.value = it.sources
                    sources.value = SourceResource.Success(response)
                }
            } else {
                //errorString.value = "Something Went Wrong : ${sourceResponse.message()}"
                sources.value = SourceResource.Error(sourceResponse.message())
            }

        }
    }

    fun getLiveSources(countryCode: String) :LiveData<SourceResource> {
        if (sources.value == null) {
            fetchSourcesFromServer(countryCode)
        }
        return sources
    }

     fun addSourcesToDatabase(sourceList :List<SourceSelect>) {
        viewModelScope.launch(Dispatchers.IO) {
            NewsRepository.getRepoInstance(getApplication()).addSourceToDatabase(sourceList)
            Log.d(TAG, "addSourcesToDatabase: data Added to Room database")
        }
    }




    fun getSourcesFromLiveData() = NewsRepository
            .getRepoInstance(getApplication()).getSourceFromDatabase()


    private fun fetchHeadlines(countryCode: String) {
        NewsRepository.getRepoInstance(getApplication())
            .fetchHeadlinesFromServer(countryCode , object :OnNewsCallback {

                override fun onSuccessResponse(response: News) {
                    newsArticleList.value = response.articleList.map{
                        it.author = "By : ${it.author}" ;
                        it.source.sourceName = "Source : ${it.source.sourceName}"
                        it
                    }
                }

                override fun onErrorResponse(errorMsg: String) {
                    //errorString.value = errorMsg
                }
            })


            }


    fun getLiveHeadlines(countryCode: String) :LiveData<List<Articles>> {
        if (newsArticleList.value == null) {
            fetchHeadlines(countryCode)
        }
        return newsArticleList
    }

    fun getSourceNews(sourceCode :String) {
        NewsRepository.getRepoInstance(getApplication()).fetchSourceNewsFromServer(
                sourceCode , object :OnNewsCallback {
            override fun onSuccessResponse(response: News) {
                sourceNewsArticlesList.value = response.articleList
            }

            override fun onErrorResponse(errorMsg: String) {
                //errorString.value = errorMsg
            }

        })

    }

    fun getLiveSourceNews() :LiveData<List<Articles>> = sourceNewsArticlesList

    /*val getError :LiveData<String>
        get() = errorString
*/


    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ${hashCode()}")
    }
}