package com.app.news_mvvm.interfaces

import com.app.news_mvvm.model.SourceSelect
import com.app.news_mvvm.model.Sources

interface OnSourceCallback {
    fun  onSuccessResponse(sourceList: List<SourceSelect>)

    fun onErrorResponse(message :String)
}