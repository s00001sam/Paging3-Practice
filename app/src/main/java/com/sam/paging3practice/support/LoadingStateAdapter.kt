package com.sam.paging3practice.support

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sam.paging3practice.databinding.ItemStateBinding


class LoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadingStateAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemStateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {

            if (loadState is LoadState.Loading) {
                binding.progress.isVisible = true
                binding.tvError.isVisible = false
                binding.btnRetry.isVisible = false

            } else {
                binding.progress.isVisible = false
            }

            if (loadState is LoadState.Error) {
                binding.tvError.isVisible = true
                binding.btnRetry.isVisible = true
                binding.tvError.text = loadState.error.localizedMessage
            }

            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemStateBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder.from(parent)
    }
}