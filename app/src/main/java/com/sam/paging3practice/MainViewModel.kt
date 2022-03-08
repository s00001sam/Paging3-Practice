package com.sam.paging3practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.sam.paging3practice.repo.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    @ExperimentalPagingApi
    fun getAttractions()  = repository.getAttractions().cachedIn(viewModelScope)
}