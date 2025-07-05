package com.oliverspryn.android.quicke2e.sample.wrappers

import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvcFactory
import com.oliverspryn.android.quicke2e.sample.photos.PhotosAdapter
import com.oliverspryn.android.quicke2e.sample.photos.PhotosModel
import com.oliverspryn.android.quicke2e.sample.photos.photolistitem.PhotoListItemViewMvc
import javax.inject.Inject

class PhotosFactory @Inject constructor() {

    fun newAdapterInstance(
        listener: PhotoListItemViewMvc.Listener,
        photos: List<PhotosModel> = emptyList(),
        viewMvcFactory: ViewMvcFactory
    ) = PhotosAdapter(
        listener,
        photos,
        viewMvcFactory
    )
}
