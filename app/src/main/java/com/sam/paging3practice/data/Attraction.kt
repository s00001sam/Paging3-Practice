package com.sam.paging3practice.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "attraction_table")
@Parcelize
data class Attraction(
    @PrimaryKey(autoGenerate = true)
    val primeryId: Int = 0,
    val id : Int? = null,
    val name: String? = null,
    val introduction: String? = null,
    val address: String? = null,
    val open_time: String? =null,
    val modified: String? = null
) : Parcelable