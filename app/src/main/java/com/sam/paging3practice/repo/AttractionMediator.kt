 package com.sam.paging3practice.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.sam.paging3practice.data.Attraction
import com.sam.paging3practice.data.RoomKey
import com.sam.paging3practice.room.RoomDB
import retrofit2.HttpException
import java.io.IOException


@ExperimentalPagingApi
class AttractionMediator(
    private val api: AttractionApi,
    private val roomDB: RoomDB
) : RemoteMediator<Int, Attraction>() {

    companion object {
        private const val FIRST_PAGE = 1
        private const val CATEGORY_ID = 61
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Attraction>): MediatorResult {
        val key = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                getKey()
            }
        }

        try {
            if (key != null) {
                if (key.isEnd) return MediatorResult.Success(endOfPaginationReached = true)
            }

            val page: Int = key?.nextKey ?: FIRST_PAGE
            val apiResponse = api.getAttractions(CATEGORY_ID, page)

            val list = apiResponse.data

            val isEnd = page >= apiResponse.totalPage()

            roomDB.withTransaction {
                val nextKey = page + 1

                roomDB.keyDao().insertKey(
                    RoomKey(0, nextKey, isEnd)
                )
                roomDB.attractionDao().insertAttractions(list ?: listOf())
            }

            return MediatorResult.Success(endOfPaginationReached = isEnd)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKey(): RoomKey? {
        return roomDB.keyDao().getKeys().firstOrNull()
    }


}