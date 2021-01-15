package com.app.news_mvvm.fragments.homefragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.SourcePagerAdapter
import com.app.news_mvvm.model.SourceSelect
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class SourceFragment : Fragment() {


    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewModel :NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel ::class.java]

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        tabLayout = requireActivity().findViewById(R.id.home_tabLayout)
        viewPager2 = view.findViewById(R.id.source_VPager)



        viewModel.getSourcesFromLiveData().observe(viewLifecycleOwner , {
            viewPager2.adapter = SourcePagerAdapter(requireActivity() , it)
            setupTabMediator(tabLayout , viewPager2 , it)
        })


    }

    private fun setupTabMediator(tabLayout: TabLayout, viewPager2: ViewPager2, it: List<SourceSelect>?) {

        TabLayoutMediator(tabLayout , viewPager2) { tab , position ->
            run {
                it?.let {
                    tab.text = it[position].sourceName
                }
            }

        }.attach()
    }

}