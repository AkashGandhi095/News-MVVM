package com.app.news_mvvm.utils

import com.app.news_mvvm.model.Sources

sealed class SourceResource {
    class Success(val data :Sources) :SourceResource()
    class Error(val errorMsg :String) :SourceResource()
    object Loading : SourceResource()
}
