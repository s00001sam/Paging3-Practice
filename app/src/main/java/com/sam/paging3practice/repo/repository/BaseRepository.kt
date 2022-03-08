package com.sam.paging3practice.repo.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sam.paging3practice.data.Attraction
import com.sam.paging3practice.data.AttractionData.Companion.PAGE_NUM
import com.sam.paging3practice.repo.AttractionApi
import com.sam.paging3practice.repo.AttractionMediator
import com.sam.paging3practice.room.RoomDB
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseRepository @Inject constructor(
    private val attractionApi: AttractionApi,
    private val roomDB: RoomDB
): Repository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAttractions(): Flow<PagingData<Attraction>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_NUM,
                initialLoadSize = 1,
                enablePlaceholders = false
            ),
            remoteMediator = AttractionMediator(
                attractionApi,
                roomDB
            ),
            pagingSourceFactory = {
                roomDB.attractionDao().getAttractions()
            }
        ).flow
    }
}