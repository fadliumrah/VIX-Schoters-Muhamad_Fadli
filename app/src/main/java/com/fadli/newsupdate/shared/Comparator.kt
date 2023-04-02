package com.fadli.newsupdate.shared

import androidx.recyclerview.widget.DiffUtil
import com.fadli.newsupdate.data.NewsArticle

class Comparator : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle) =
        oldItem == newItem
}