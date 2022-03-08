package com.sam.paging3practice.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.paging3practice.data.Attraction

@Dao
interface AttractionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttractions(list: List<Attraction>)

    @Query("SELECT * FROM attraction_table")
    fun getAttractions(): PagingSource<Int, Attraction>

    @Query("DELETE FROM attraction_table")
    suspend fun clear()

    @Query("SELECT COUNT(id) from attraction_table")
    suspend fun countId(): Int

}