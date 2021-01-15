package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.NewsAdapter
import com.app.news_mvvm.model.News
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.textview.MaterialTextView

private const val SOURCE_ID = "sourceId"

/**
 * A simple [Fragment] subclass.
 * Use the [SubSourceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubSourceFragment : Fragment() , NewsAdapter.OnNewsClickListener {
    private var param1: String? = null
  
    private lateinit var viewModel :NewsViewModel
    private lateinit var sourceNewsRec :RecyclerView
    private lateinit var newsAdapter :NewsAdapter
    private val bundle:Bundle by lazy { Bundle() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel ::class.java]
        arguments?.let {
            param1 = it.getString(SOURCE_ID)
            Log.d(TAG, "onCreate: $param1")
            viewModel.getSourceNews(param1?:"")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_source, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        viewModel.getLiveSourceNews().observe(viewLifecycleOwner , {
            sourceNewsRec.setBackgroundColor(resources.getColor(R.color.white , null))
            newsAdapter.updateArticles(it)
        })
    }

    private fun initViews(view: View) {
        sourceNewsRec = view.findViewById(R.id.sourceNews_recV)
        newsAdapter = NewsAdapter( listOf() , this)

        sourceNewsRec.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }


    }


    companion object {
        private const val TAG = "SubSourceFragment"

        @JvmStatic
        fun newInstance(param1: String) =
            SubSourceFragment().apply {
                arguments = Bundle().apply {
                    putString(SOURCE_ID, param1)

                }
            }
    }

    override fun onClick(itemView: View, url: String) {
        bundle.putString(AppConstants.NEWS_URL , url)
        itemView.findNavController().navigate(R.id.action_sourceFragment_to_newsDetailsFragment , bundle)
    }


}