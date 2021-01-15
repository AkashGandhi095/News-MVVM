package com.app.news_mvvm.utils

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.app.news_mvvm.HomeActivity
import com.app.news_mvvm.MainActivity

class MainClass :AppCompatActivity() {



    private val preference :AppPreference by lazy { AppPreference(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (preference.isSelectionDone) {
            startActivity(Intent(this , HomeActivity ::class.java))
        }
        else {
            startActivity(Intent(this , MainActivity ::class.java))
        }
        finish()

    }


}