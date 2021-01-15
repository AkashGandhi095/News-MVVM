package com.app.news_mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Sources (val sources :List<SourceSelect>)

@Entity
data class SourceSelect (

        var isSelected :Boolean ,

        @SerializedName("name")
        val sourceName :String ,

        @SerializedName ("id")
        val sourceId :String
) {
        @PrimaryKey(autoGenerate = true)
        var autoId :Int = 0
}
