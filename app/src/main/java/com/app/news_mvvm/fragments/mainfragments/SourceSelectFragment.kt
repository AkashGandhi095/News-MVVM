package com.app.news_mvvm.fragments.mainfragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.HomeActivity
import com.app.news_mvvm.MainActivity
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.SourceSelectAdapter
import com.app.news_mvvm.model.SourceSelect
import com.app.news_mvvm.utils.AppPreference
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.log


class SourceSelectFragment : Fragment(R.layout.fragment_source_select) , SourceSelectAdapter.SourceCheckedListener {

    companion object {
        private const val TAG = "SourceSelectFragmentLog"
    }
    private lateinit var sourceRecyclerView: RecyclerView
    private lateinit var sourceSelectAdapter: SourceSelectAdapter
    private val selectedSourceList :ArrayList<SourceSelect> by lazy { arrayListOf() }
    private lateinit var openMainBtn :MaterialButton
    private lateinit var newsViewModel :NewsViewModel

    private val pref :AppPreference by lazy {
        AppPreference(requireContext())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(requireActivity())[NewsViewModel ::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        newsViewModel.getLiveSources(newsViewModel.countryCode).observe(viewLifecycleOwner, {
            sourceSelectAdapter.updateSourceSelectList(it)
        })

        openMainBtn.setOnClickListener {

            newsViewModel.addSourcesToDatabase(selectedSourceList)
            pref.editActivityStatus(true)
            requireActivity().startActivity(Intent(requireContext() , HomeActivity ::class.java))
            requireActivity().finish()

        }
    }

    private fun initViews(view: View) {
        openMainBtn = view.findViewById(R.id.open_mainBtn)
        sourceRecyclerView = view.findViewById(R.id.sourceSelect_recV)
        sourceSelectAdapter = SourceSelectAdapter(listOf() , this)
        sourceRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sourceSelectAdapter

        }
    }



    override fun onSourceChecked(sourceSelect: SourceSelect , pos :Int) {

        if (sourceSelect.isSelected) {
            selectedSourceList.add(sourceSelect)
        }
        else {
            selectedSourceList.remove(sourceSelect)
        }

        openMainBtn.visibility = if (selectedSourceList.size > 0) View.VISIBLE else View.GONE
        Log.d(TAG, "onSourceChecked: list : $selectedSourceList ")
    }
}