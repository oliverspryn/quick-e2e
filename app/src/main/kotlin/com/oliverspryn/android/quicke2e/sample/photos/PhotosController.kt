package com.oliverspryn.android.quicke2e.sample.photos

import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import com.oliverspryn.android.quicke2e.sample.R
import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvcFactory
import com.oliverspryn.android.quicke2e.sample.photos.photolistitem.PhotoListItemViewMvc
import com.oliverspryn.android.quicke2e.sample.wrappers.PhotosFactory
import com.oliverspryn.android.quicke2e.sample.wrappers.SchedulerForwarder
import com.oliverspryn.android.quicke2e.sample.wrappers.android.ToastForwarder
import com.oliverspryn.android.quicke2e.sample.wrappers.navigation.NavDirectionsForwarder
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class PhotosController @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val navController: NavController,
    private val navDirectionsForwarder: NavDirectionsForwarder,
    private val photosApiService: PhotosApiService,
    private val photosFactory: PhotosFactory,
    private val schedulerForwarder: SchedulerForwarder,
    private val toastForwarder: ToastForwarder,
    private val viewMvcFactory: ViewMvcFactory
) : PhotoListItemViewMvc.Listener {

    @VisibleForTesting
    var photosAdapter: PhotosAdapter? = null

    var viewMvc: PhotosViewMvc? = null

    fun onStart() {
        val adapter = photosFactory.newAdapterInstance(
            listener = this,
            viewMvcFactory = viewMvcFactory
        )

        viewMvc?.setAdapter(adapter)
        getPhotos()

        photosAdapter = adapter
    }

    fun onDestroy() {
        compositeDisposable.clear()
    }

    // region PhotoListItemViewMvc

    override fun onPhotoTap(photoModel: PhotosModel) {
        val directions = navDirectionsForwarder.actionPhotoListFragmentToDetailsFragment(photoModel)
        navController.navigate(directions)
    }

    // endregion

    @VisibleForTesting
    fun getPhotos() {
        photosApiService
            .getPhotos()
            .subscribeOn(schedulerForwarder.io)
            .observeOn(schedulerForwarder.mainThread)
            .subscribe({ photos ->
                photosAdapter?.updatePhotos(photos)
            }, {
                viewMvc?.rootView?.context?.let { context ->
                    val message = context.getString(R.string.network_error)

                    toastForwarder.makeText(
                        context,
                        message,
                        Toast.LENGTH_LONG
                    )?.show()
                }
            })
            .let {
                compositeDisposable.add(it)
            }
    }
}
