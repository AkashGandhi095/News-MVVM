package com.app.news_mvvm.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.news_mvvm.model.SourceSelect

@Dao
interface SourceDao {

    @Insert
    suspend fun insertSourceList(sourceList : List<SourceSelect>)

    @Query("Select * from SourceSelect")
    fun getAllSources() :LiveData<List<SourceSelect>>

}