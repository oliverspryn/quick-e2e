package com.oliverspryn.android.edgetoedge.photos.photolistitem

import com.oliverspryn.android.edgetoedge.photos.PhotosModel
import com.oliverspryn.android.edgetoedge.mvc.ObservableViewMvc

interface PhotoListItemViewMvc : ObservableViewMvc<PhotoListItemViewMvc.Listener> {
    interface Listener {
        fun onPhotoTap(photoModel: PhotosModel)
    }

    fun bindPhoto(photoModel: PhotosModel)
}
