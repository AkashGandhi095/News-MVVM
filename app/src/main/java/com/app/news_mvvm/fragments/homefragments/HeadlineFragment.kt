package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.NewsAdapter
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.utils.AppPreference
import com.app.news_mvvm.viewModel.NewsViewModel



class HeadlineFragment : Fragment() , NewsAdapter.OnNewsClickListener{

    companion object {
        private const val TAG = "HeadlineFragmentScreen"
    }
    private lateinit var viewModel :NewsViewModel
    private lateinit var headlinesRecV :RecyclerView
    private lateinit var newsAdapter: NewsAdapter
    private val bundle :Bundle by lazy { Bundle() }

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
        viewModel.getLiveHeadlines(pref.prefCountryCode).observe(viewLifecycleOwner, {
            Log.d(TAG, "onViewCreated: res : $it")
            headlinesRecV.setBackgroundColor(resources.getColor(R.color.white , null))
            newsAdapter.updateArticles(it)

        })
    }

    private fun initViews(view: View) {
        headlinesRecV = view.findViewById(R.id.newsRecView)
        newsAdapter = NewsAdapter(listOf() , this)
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