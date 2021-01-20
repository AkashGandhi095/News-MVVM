package com.app.news_mvvm.utils

import com.app.news_mvvm.model.News

sealed class NewsResource {

    class Success(val news :News) :NewsResource()
    class Error(val errorMsg :String) :NewsResource()
    object Loading :NewsResource()

}
