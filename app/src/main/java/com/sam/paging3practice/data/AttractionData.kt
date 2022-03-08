package com.sam.paging3practice.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionData (
    var total: Int? = null,
    val data: List<Attraction>? = null
): Parcelable {
    companion object {
        const val PAGE_NUM = 30
    }

    fun totalPage(): Int {
        val remNum = total?.rem(PAGE_NUM) ?: 0
        val page = total?.div(PAGE_NUM) ?: 1
        if (remNum != 0) {
            return page + 1
        } else {
            return page
        }
    }
}