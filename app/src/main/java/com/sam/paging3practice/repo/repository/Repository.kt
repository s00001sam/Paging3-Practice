package com.sam.paging3practice.repo.repository

import androidx.paging.PagingData
import com.sam.paging3practice.data.Attraction
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getAttractions(): Flow<PagingData<Attraction>>
}