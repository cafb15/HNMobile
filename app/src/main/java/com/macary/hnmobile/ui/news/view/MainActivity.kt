package com.macary.hnmobile.ui.news.view

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.macary.hnmobile.databinding.ActivityMainBinding
import com.macary.hnmobile.ui.news.NewsViewModel
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.macary.hnmobile.ui.news.NewsViewModel.UiModel
import com.macary.hnmobile.ui.news.view.adapter.ISwipeListener
import com.macary.hnmobile.ui.news.view.adapter.NewsRecyclerAdapter
import com.macary.hnmobile.ui.news.view.adapter.SwipeNewsHelper
import com.macary.hnmobile.ui.newsDetail.view.NewsDetailActivity
import com.macary.hnmobile.utils.networkMonitor.ConnectionStateMonitor
import com.macary.hnmobile.utils.Constants
import com.macary.hnmobile.utils.Utils
import com.macary.hnmobile.utils.adapters.AbstractAdapter
import com.macary.hnmobile.utils.extensions.startActivity
import com.macary.hnmobile.utils.extensions.toastLong

class MainActivity : ScopeActivity(), ISwipeListener {

    private val viewModel: NewsViewModel by viewModel()
    private val monitor: ConnectionStateMonitor by inject()

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NewsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        ui()
        events()

        viewModel.model.observe(this@MainActivity, Observer(this::updateUi))
        viewModel.getNews()
        viewModel.updateNews()
    }

    override fun onResume() {
        super.onResume()
        monitor.enable { state ->
            if (state) {
                viewModel.updateNews()
            }
        }
    }

    private fun ui() {
        with(binding.rvNews) {
            itemAnimator = DefaultItemAnimator()
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(DividerItemDecoration(this@MainActivity, LinearLayout.VERTICAL))
            ItemTouchHelper(SwipeNewsHelper(this@MainActivity)).attachToRecyclerView(this)
        }
    }

    private fun events() {
        binding.srlRefresh.setOnRefreshListener { viewModel.updateNews() }
    }

    private fun updateUi(model: UiModel) = with(binding) {
        when (model) {
            is UiModel.Loading -> {
                if (!srlRefresh.isRefreshing) {
                    srlRefresh.isRefreshing = true
                }
            }
            is UiModel.NoLoading -> srlRefresh.isRefreshing = false
            is UiModel.Success -> {
                adapter = NewsRecyclerAdapter(model.news) {
                    startActivity<NewsDetailActivity> { putExtra(Constants.extraNews, Utils.toJson(it)) }
                }

                rvNews.adapter = adapter
            }
            is UiModel.Error -> toastLong(model.message)
        }
    }

    override fun onSwiped(holder: AbstractAdapter.Holder, direction: Int, position: Int) {
        val news = adapter.getItem(position)

        viewModel.deleteNews(news)
        adapter.remove(position)
    }

    override fun onStop() {
        super.onStop()
        monitor.disable()
    }
}