package com.oliverspryn.android.edgetoedge.wrappers.navigation

import android.net.Uri
import com.oliverspryn.android.edgetoedge.details.DetailsFragmentDirections
import com.oliverspryn.android.edgetoedge.photos.PhotosFragmentDirections
import com.oliverspryn.android.edgetoedge.photos.PhotosModel
import javax.inject.Inject

class NavDirectionsForwarder @Inject constructor() {
    fun actionDetailsFragmentToPreviewPictureUrl(
        uri: Uri
    ) = DetailsFragmentDirections.actionDetailsFragmentToPreviewPictureUrl(
        uri
    )

    fun actionPhotoListFragmentToDetailsFragment(
        photo: PhotosModel
    ) = PhotosFragmentDirections.actionPhotoListFragmentToDetailsFragment(
        photo
    )
}
