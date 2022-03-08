package com.sam.paging3practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.sam.paging3practice.databinding.ActivityMainBinding
import com.sam.paging3practice.support.AttractionAdapter
import com.sam.paging3practice.support.LoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private val attractionAdapter: AttractionAdapter by lazy { AttractionAdapter() }
    private val loadingStateAdapter: LoadingStateAdapter by lazy {
        LoadingStateAdapter{ attractionAdapter.retry() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        collectFlow()
    }

    private fun initView() {
        binding.rcyAttraction.adapter = attractionAdapter.withLoadStateFooter(footer = loadingStateAdapter)
        attractionAdapter.addLoadStateListener { loadState ->

            if (loadState.mediator?.refresh is LoadState.Loading) {
                binding.progress.isVisible = true
            } else {
                binding.progress.isVisible = false
                binding.swAll.isRefreshing = false
            }
        }

        binding.swAll.setOnRefreshListener {
            attractionAdapter.refresh()
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    private fun collectFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getAttractions().collectLatest {
                    attractionAdapter.submitData(it)
                }
            }
        }
    }
}