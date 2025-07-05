package com.oliverspryn.android.quicke2e.sample.mvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oliverspryn.android.quicke2e.sample.MainViewMvc
import com.oliverspryn.android.quicke2e.sample.MainViewMvcImpl
import com.oliverspryn.android.quicke2e.sample.details.DetailsViewMvc
import com.oliverspryn.android.quicke2e.sample.details.DetailsViewMvcImpl
import com.oliverspryn.android.quicke2e.sample.home.HomeViewMvc
import com.oliverspryn.android.quicke2e.sample.home.HomeViewMvcImpl
import com.oliverspryn.android.quicke2e.sample.photos.PhotosViewMvc
import com.oliverspryn.android.quicke2e.sample.photos.PhotosViewMvcImpl
import com.oliverspryn.android.quicke2e.sample.photos.photolistitem.PhotoListItemViewMvc
import com.oliverspryn.android.quicke2e.sample.photos.photolistitem.PhotoListItemViewMvcImpl
import com.oliverspryn.android.quicke2e.sample.wrappers.android.LinearLayoutManagerFactory
import javax.inject.Inject

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater
) {

    fun getDetailsViewMvc(
        parent: ViewGroup?
    ): DetailsViewMvc = DetailsViewMvcImpl(
        inflater = layoutInflater,
        parent = parent
    )

    fun getHomeViewMvc(
        parent: ViewGroup?
    ): HomeViewMvc = HomeViewMvcImpl(
        inflater = layoutInflater,
        parent = parent
    )

    fun getMainViewMvc(
        parent: ViewGroup?,
    ): MainViewMvc = MainViewMvcImpl(
        inflater = layoutInflater,
        parent = parent
    )

    fun getPhotoListItemViewMvc(
        parent: ViewGroup?
    ): PhotoListItemViewMvc = PhotoListItemViewMvcImpl(
        inflater = layoutInflater,
        parent = parent
    )

    fun getPhotosViewMvc(
        linearLayoutManagerFactory: LinearLayoutManagerFactory,
        parent: ViewGroup?
    ): PhotosViewMvc = PhotosViewMvcImpl(
        inflater = layoutInflater,
        linearLayoutManagerFactory = linearLayoutManagerFactory,
        parent = parent
    )
}
