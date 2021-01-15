package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.app.news_mvvm.R
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.viewModel.NewsViewModel


class NewsDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    companion object {
        private const val TAG = "NewsDetailsFragment"
    }
    private var param1: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(AppConstants.NEWS_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsWeb = view.findViewById<WebView>(R.id.web_newsV)
        Log.d(TAG, "onViewCreated: ${param1?:"Null URL"}")
        newsWeb.loadUrl(param1?:"")
    }


}