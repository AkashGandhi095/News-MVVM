package com.app.news_mvvm.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("articles")
    val articleList: List<Articles>
)

data class Articles(

    val source: Source ,
    var author :String ,
    val title :String ,
    @SerializedName("description")
    val des :String ,
    @SerializedName("url")
    val newsUrl :String ,
    @SerializedName("urlToImage")
    val imgUrl :String ,
    val publishedAt :String
)

data class Source(@SerializedName("name") var sourceName :String)
