package com.macary.hnmobile.ui.news.view.adapter

import android.view.View
import com.macary.domain.News
import com.macary.hnmobile.R
import com.macary.hnmobile.utils.adapters.AbstractAdapter

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
class NewsRecyclerAdapter(news: List<News>, private val f: (News) -> Unit)
    : AbstractAdapter<News>(news, R.layout.item_news) {

    override fun onItemClick(itemView: View, position: Int) {
        f(items[position])
    }

    override fun Holder.bind(item: News, position: Int) {
        binding.tvTitleNews.text = item.title
        binding.tvAuthorNews.text = item.authorTime
    }
}