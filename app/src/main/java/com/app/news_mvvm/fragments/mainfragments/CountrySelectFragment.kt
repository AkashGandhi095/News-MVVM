package com.app.news_mvvm.fragments.mainfragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.app.news_mvvm.R
import com.app.news_mvvm.adapters.CountryAdapter
import com.app.news_mvvm.model.Country
import com.app.news_mvvm.utils.AppConstants
import com.app.news_mvvm.utils.AppPreference
import com.app.news_mvvm.viewModel.NewsViewModel
import com.google.android.material.button.MaterialButton
import java.util.prefs.Preferences


class CountrySelectFragment : Fragment(R.layout.fragment_country_select) {


    private  val TAG = "CountrySelectFragment"

    private lateinit var selectedCode :String
    private lateinit var viewModel :NewsViewModel

    private val pref :AppPreference by lazy {
       AppPreference(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel ::class.java]
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: ")
        val nextButton :MaterialButton = view.findViewById(R.id.next_Btn)
        val autoCompleteView :AutoCompleteTextView = view.findViewById(R.id.autoComplete)

        val adapter = ArrayAdapter(requireContext() , R.layout.country_list , getCountryList())
        autoCompleteView.setAdapter(adapter)

        autoCompleteView.setOnItemClickListener { _, _, position, _ ->
            selectedCode = getCountryList()[position].countryCode

            if (nextButton.visibility == View.INVISIBLE) {
                nextButton.visibility = View.VISIBLE
            }
        }

        nextButton.setOnClickListener {
            //val bundle = bundleOf("C_CODE" to selectedCode)
            viewModel.countryCode = selectedCode
           // preferences.edit { putString(AppConstants.PREF_COUNTRY_CODE , selectedCode) }
            pref.editCountryCode(selectedCode)
            it.findNavController().navigate(R.id.action_countrySelectFragment_to_sourceSelectFragment)
        }

    }

    private fun getCountryList() :List<Country> = arrayListOf<Country>().apply {
        add(Country("India" , "in"))
        add(Country("USA" , "us"))
        add(Country("Canada" , "ca"))
        add(Country("Australia" , "au"))

    }

}