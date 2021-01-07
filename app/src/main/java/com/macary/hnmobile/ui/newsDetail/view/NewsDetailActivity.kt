package com.macary.hnmobile.ui.newsDetail.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.macary.domain.News
import com.macary.hnmobile.R
import com.macary.hnmobile.databinding.ActivityNewsDetailBinding
import com.macary.hnmobile.ui.newsDetail.NewsWebClient
import com.macary.hnmobile.utils.Constants
import com.macary.hnmobile.utils.Utils
import com.macary.hnmobile.utils.extensions.onClick

class NewsDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        events()

        intent.extras?.let {
            val news: News = Utils.fromJson(it.getString(Constants.extraNews), News())

            with(binding) {
                tvTitleNews.text = news.title
                wbNews.webChromeClient = NewsWebClient(pbLoading)
                wbNews.settings.domStorageEnabled = true
                wbNews.settings.javaScriptEnabled = true
                wbNews.loadUrl(news.url)
            }
        }
    }

    private fun events() {
        binding.tvBack.onClick { onBackPressed() }
    }
}