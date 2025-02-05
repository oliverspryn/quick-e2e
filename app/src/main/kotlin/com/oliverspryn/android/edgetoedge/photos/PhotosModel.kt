package com.oliverspryn.android.edgetoedge.photos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotosModel(
    val id: Int,
    val thumbnailUrl: String,
    val title: String,
    val url: String
) : Parcelable
