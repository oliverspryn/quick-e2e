package com.oliverspryn.android.quicke2e.sample.wrappers.navigation

import android.net.Uri
import com.oliverspryn.android.quicke2e.sample.details.DetailsFragmentDirections
import com.oliverspryn.android.quicke2e.sample.photos.PhotosFragmentDirections
import com.oliverspryn.android.quicke2e.sample.photos.PhotosModel
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
