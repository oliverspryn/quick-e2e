package com.oliverspryn.android.edgetoedge.wrappers

import com.oliverspryn.android.edgetoedge.mvc.ViewMvcFactory
import com.oliverspryn.android.edgetoedge.photos.PhotosAdapter
import com.oliverspryn.android.edgetoedge.photos.PhotosModel
import com.oliverspryn.android.edgetoedge.photos.photolistitem.PhotoListItemViewMvc
import javax.inject.Inject

class PhotosFactory @Inject constructor() {

    fun newAdapterInstance(
        listener: PhotoListItemViewMvc.Listener,
        photos: List<PhotosModel> = emptyList(),
        picassoForwarder: PicassoForwarder,
        viewMvcFactory: ViewMvcFactory
    ) = PhotosAdapter(
        listener,
        photos,
        picassoForwarder,
        viewMvcFactory
    )
}
