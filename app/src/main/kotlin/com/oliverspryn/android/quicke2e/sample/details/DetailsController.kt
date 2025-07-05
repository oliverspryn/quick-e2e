package com.oliverspryn.android.quicke2e.sample.details

import androidx.navigation.NavController
import com.oliverspryn.android.quicke2e.sample.photos.PhotosModel
import com.oliverspryn.android.quicke2e.sample.wrappers.navigation.NavDirectionsForwarder
import com.oliverspryn.android.quicke2e.sample.wrappers.android.UriForwarder
import javax.inject.Inject

class DetailsController @Inject constructor(
    private val navController: NavController,
    private val navDirectionsForwarder: NavDirectionsForwarder,
    private val uriForwarder: UriForwarder
) : DetailsViewMvc.Listener {

    var viewMvc: DetailsViewMvc? = null

    fun onCreateView(model: PhotosModel) {
        viewMvc?.setPhoto(model.url)
        viewMvc?.setTitle(model.title)
    }

    fun onStart() {
        viewMvc?.registerListener(listener = this)
    }

    fun onDestroy() {
        viewMvc?.unregisterListener(listener = this)
    }

    // region DetailsViewMvc.Listener

    override fun onPreviewTap(uri: String) {
        val convertedUri = uriForwarder.parse(uri)
        val directions =
            navDirectionsForwarder.actionDetailsFragmentToPreviewPictureUrl(convertedUri)

        navController.navigate(directions)
    }

    // endregion
}
