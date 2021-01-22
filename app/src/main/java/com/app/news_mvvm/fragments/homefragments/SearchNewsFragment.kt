package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.NewsAdapter
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.utils.NewsResource
import com.app.news_mvvm.utils.setVisibility
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchNewsFragment : Fragment(R.layout.fragment_search_news) , NewsAdapter.OnNewsClickListener {

    companion object {
        private const val TAG = "SearchNewsFragment"
    }
    private lateinit var newsViewModel :NewsViewModel
    private lateinit var searchRecV :RecyclerView
    private lateinit var newsAdapter :NewsAdapter
    private lateinit var searchEditText :AppCompatEditText
    private lateinit var progressV :ProgressBar
    private val bundle :Bundle by lazy { Bundle() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel ::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        searchNews()
        subscribeObserver()

    }


    private fun initViews(view: View) {
        progressV = view.findViewById(R.id.progressV)
        searchEditText = view.findViewById(R.id.editSearchV)
        searchRecV = view.findViewById(R.id.searchRecV)
        newsAdapter = NewsAdapter(this)
        searchRecV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    private var job :Job? = null
    private fun searchNews() {
        searchEditText.addTextChangedListener { editable ->
            job?.cancel()
            job = viewLifecycleOwner.lifecycleScope.launch {
                Log.d(TAG, "searchNews: ${this.hashCode()}")
                editable?.let {
                    delay(1000L)
                    if (editable.isNotEmpty()) {

                        Log.d(TAG, "searchNews: query : ${editable.toString()}")
                        newsViewModel.fetchSearchNews(editable.toString())
                    }
                }
            }
            
        }
    }

    private fun subscribeObserver() {
        newsViewModel.getLiveSearchedNews.observe(viewLifecycleOwner, { newsResource ->
            when (newsResource) {

                is NewsResource.Success -> {
                    newsAdapter.diffList.submitList(newsResource.news.articleList)
                    progressV.setVisibility(false)
                }

                is NewsResource.Error -> {
                    progressV.setVisibility(false)
                    Log.d(TAG, "subscribeObserver: ERROR : ${newsResource.errorMsg}")
                }

                is NewsResource.Loading -> {
                    progressV.setVisibility(true)
                }
            }
        })
    }
    override fun onClick(itemView: View, url: String) {
        bundle.apply {
            putString(AppConstants.NEWS_URL , url)
        }
        itemView.findNavController()
                .navigate(R.id.action_searchNewsFragment_to_newsDetailsFragment , bundle)
    }


}