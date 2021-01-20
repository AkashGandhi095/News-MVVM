package com.app.news_mvvm.utils

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.setVisibility(isVisible :Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}