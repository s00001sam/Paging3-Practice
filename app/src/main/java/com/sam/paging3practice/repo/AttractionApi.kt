package com.sam.paging3practice.repo

import com.sam.paging3practice.data.AttractionData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AttractionApi {

    @Headers("accept:application/json")
    @GET("/open-api/zh-tw/Attractions/All")
    suspend fun getAttractions(
        @Query("categoryIds") categoryIds: Int = 61,
        @Query("page") page: Int? = null,
    ): AttractionData

}