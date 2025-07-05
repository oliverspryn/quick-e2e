package com.oliverspryn.android.quicke2e.sample.photos

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PhotosModel(
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) : Parcelable
