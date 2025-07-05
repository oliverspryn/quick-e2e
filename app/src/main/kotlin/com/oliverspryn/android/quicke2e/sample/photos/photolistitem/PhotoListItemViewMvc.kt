package com.oliverspryn.android.quicke2e.sample.photos.photolistitem

import com.oliverspryn.android.quicke2e.sample.mvc.ObservableViewMvc
import com.oliverspryn.android.quicke2e.sample.photos.PhotosModel

interface PhotoListItemViewMvc : ObservableViewMvc<PhotoListItemViewMvc.Listener> {
    interface Listener {
        fun onPhotoTap(photoModel: PhotosModel)
    }

    fun bindPhoto(photoModel: PhotosModel)
}
