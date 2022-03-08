package com.sam.paging3practice.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sam.paging3practice.data.RoomKey

@Dao
interface KeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(remoteKey: RoomKey)

    @Query("SELECT * FROM room_keys")
    suspend fun getKeys():List<RoomKey>
}