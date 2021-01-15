package com.app.news_mvvm.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.news_mvvm.fragments.homefragments.SubSourceFragment
import com.app.news_mvvm.model.SourceSelect

class SourcePagerAdapter(fa: FragmentActivity , private var pageList:List<SourceSelect>) :
    FragmentStateAdapter(fa) {


    override fun getItemCount():Int = pageList.size

    override fun createFragment(position: Int): Fragment {

       return SubSourceFragment.newInstance(pageList[position].sourceId)
    }

}