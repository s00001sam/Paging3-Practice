package com.sam.paging3practice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_keys")
data class RoomKey (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val nextKey: Int? = null,
    val isEnd: Boolean
)
