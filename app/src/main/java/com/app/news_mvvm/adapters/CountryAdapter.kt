package com.app.news_mvvm.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.app.news_mvvm.R
import com.app.news_mvvm.model.Country
import com.google.android.material.textview.MaterialTextView

class CountryAdapter(context: Context ,  private val countryList: List<Country>) :
    ArrayAdapter<Country>(context , 0 , countryList) {


    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.country_list , null , true)
        val countryTextView :MaterialTextView = view.findViewById(R.id.countryName_textV)
        countryTextView.text = countryList[position].countryName
        return view
    }

}