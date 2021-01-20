package com.app.news_mvvm.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.news_mvvm.R
import com.app.news_mvvm.model.Articles
import com.app.news_mvvm.model.News
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso

class NewsAdapter( private val onNewsClickListener: OnNewsClickListener) :
    RecyclerView.Adapter<NewsAdapter.NewsHolder>() {

   /* fun updateArticles(articlesList: List<Articles>) {
        this.articlesList = articlesList
        notifyDataSetChanged()
    }*/

    private val diffUtil = object :DiffUtil.ItemCallback<Articles>() {
        override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem.newsUrl == newItem.newsUrl
        }

        override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
            return oldItem == newItem
        }
    }

    val diffList = AsyncListDiffer(this , diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsHolder {
        return NewsHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.article_row , parent , false))
    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsHolder, position: Int) {

       // holder.bindDataToView(articlesList[position])
        holder.bindDataToView(diffList.currentList[position])
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = diffList.currentList.size

    inner class NewsHolder(itemView :View) :RecyclerView.ViewHolder(itemView) {
        private val newsImgV :ShapeableImageView = itemView.findViewById(R.id.news_imgV)
        private val titleV :MaterialTextView = itemView.findViewById(R.id.news_titleV)
        private val authorV :MaterialTextView = itemView.findViewById(R.id.author_textV)
        private val sourceV :MaterialTextView = itemView.findViewById(R.id.source_textV)

        init {
            itemView.setOnClickListener {
                //it.findNavController().navigate(R.id.action_headlineFragment_to_newsDetailsFragment)
               // Log.d("NEWS_URL", ": ${articlesList[absoluteAdapterPosition].newsUrl}")
                onNewsClickListener.onClick(itemView , diffList.currentList[absoluteAdapterPosition].newsUrl)
            }
        }
        fun bindDataToView(articles :Articles) {
            Picasso.with(itemView.context).load(articles.imgUrl).fit().into(newsImgV)
            titleV.text = articles.title
            authorV.text = articles.author
            sourceV.text = articles.source.sourceName
        }
    }

    interface OnNewsClickListener {
        fun onClick(itemView: View , url :String)
    }
}