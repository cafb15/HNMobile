package com.macary.hnmobile.ui.newsDetail

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import com.macary.hnmobile.utils.extensions.hide

/**
 * Created by Carlos Farfan on 5/01/2021.
 */
class NewsWebClient(private val pbLoading: ProgressBar) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        pbLoading.progress = newProgress

        if (newProgress == 100) {
            pbLoading.hide()
        }
    }
}