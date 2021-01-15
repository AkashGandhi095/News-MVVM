package com.app.news_mvvm.interfaces

import com.app.news_mvvm.model.News

interface OnNewsCallback {
    fun onSuccessResponse (response :News)
    fun onErrorResponse (errorMsg :String)
}