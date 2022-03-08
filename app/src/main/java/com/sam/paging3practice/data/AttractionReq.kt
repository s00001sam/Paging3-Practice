package com.sam.paging3practice.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttractionReq (
    var categoryIds: Int = 61,
    var page: Int? = null
): Parcelable