package com.app.news_mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.model.SourceSelect
import com.google.android.material.checkbox.MaterialCheckBox

class SourceSelectAdapter(private var listener: SourceCheckedListener) :
        RecyclerView.Adapter<SourceSelectAdapter.SourceHolder>() {
/*
    fun updateSourceSelectList(sourceList: List<SourceSelect>) {
        this.sourceList = sourceList
        notifyDataSetChanged()
    }*/

    private val diffUtil = object : DiffUtil.ItemCallback<SourceSelect>() {
        override fun areItemsTheSame(oldItem: SourceSelect, newItem: SourceSelect): Boolean {
            return oldItem.sourceName == newItem.sourceName
        }

        override fun areContentsTheSame(oldItem: SourceSelect, newItem: SourceSelect): Boolean {
            return oldItem == newItem
        }
    }

    val diffList = AsyncListDiffer(this , diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceSelectAdapter.SourceHolder {
       return SourceHolder(LayoutInflater.from(parent.context).inflate(R.layout.source_select_row , parent , false))
    }

    override fun onBindViewHolder(holder: SourceSelectAdapter.SourceHolder, position: Int) {
        holder.bindData(diffList.currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int = diffList.currentList.size

    inner class SourceHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val sourceSelector :MaterialCheckBox = itemView.findViewById(R.id.view_checkedB)

        init {
            sourceSelector.setOnCheckedChangeListener { _, isChecked ->

               // sourceList[adapterPosition].isSelected = isChecked
                diffList.currentList[absoluteAdapterPosition].isSelected = isChecked
                //listener.onSourceChecked(sourceList[adapterPosition] , adapterPosition)
                listener.onSourceChecked(diffList.currentList[absoluteAdapterPosition] , absoluteAdapterPosition)

            }
        }
        fun bindData(sourceSelect: SourceSelect) {
            sourceSelector.isChecked = sourceSelect.isSelected
            sourceSelector.text = sourceSelect.sourceName
        }
    }

    interface SourceCheckedListener {
        fun onSourceChecked(sourceSelect: SourceSelect , pos :Int)
    }
}