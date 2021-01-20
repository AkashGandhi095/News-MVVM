package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.NewsAdapter
import com.app.news_mvvm.model.News
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.utils.AppPreference
import com.app.news_mvvm.utils.NewsResource
import com.app.news_mvvm.utils.setVisibility
import com.app.news_mvvm.viewModel.NewsViewModel



class HeadlineFragment : Fragment() , NewsAdapter.OnNewsClickListener{

    companion object {
        private const val TAG = "HeadlineFragmentScreen"
    }
    private lateinit var viewModel :NewsViewModel
    private lateinit var headlinesRecV :RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val bundle :Bundle by lazy { Bundle() }
    private lateinit var progressV :ProgressBar

    private val pref :AppPreference by lazy { AppPreference(requireContext()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel ::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.getLiveHeadlines(pref.prefCountryCode).observe(viewLifecycleOwner, { newsResponse ->
            when (newsResponse) {

                is NewsResource.Success -> {
                    Log.d(TAG, "SUCCESS: ${newsResponse.news.articleList}")
                    newsAdapter.diffList.submitList(newsResponse.news.articleList)
                    progressV.setVisibility(false)
                }

                is NewsResource.Error -> {
                    Log.d(TAG, "ERROR:  ${newsResponse.errorMsg}")
                    progressV.setVisibility(false)

                }

                is NewsResource.Loading -> {
                    Log.d(TAG, "Loading: ")
                    progressV.setVisibility(true)

                }
            }

        })
    }

    private fun initViews(view: View) {
        progressV = view.findViewById(R.id.progressV)
        headlinesRecV = view.findViewById(R.id.newsRecView)
        newsAdapter = NewsAdapter(this)
        headlinesRecV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }


    override fun onClick(itemView: View, url: String) {
        bundle.putString(AppConstants.NEWS_URL , url)
        itemView.findNavController().navigate(R.id.action_headlineFragment_to_newsDetailsFragment , bundle)
    }


}