package com.app.news_mvvm.model

data class Country(val countryName :String , val countryCode :String) {

    override fun toString(): String {
        return countryName
    }
}
