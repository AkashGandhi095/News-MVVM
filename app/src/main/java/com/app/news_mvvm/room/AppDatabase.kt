package com.app.news_mvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.news_mvvm.model.SourceSelect

@Database(entities = [SourceSelect ::class], version = 1 , exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun sourceDao() :SourceDao

    companion object {
        @Volatile
        private var INSTANCE :AppDatabase? = null

        operator fun invoke(context: Context) :AppDatabase = INSTANCE ?: createDatabase(context).also {
            INSTANCE = it
        }

        private fun createDatabase(context: Context) :AppDatabase {
            return Room.databaseBuilder(context ,
                AppDatabase ::class.java ,
                "NewsDatabase")
                .build()
        }
    }
}