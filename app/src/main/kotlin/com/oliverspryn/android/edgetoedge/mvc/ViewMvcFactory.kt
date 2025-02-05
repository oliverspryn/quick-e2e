package com.oliverspryn.android.edgetoedge.mvc

import android.view.LayoutInflater
import android.view.ViewGroup
import com.oliverspryn.android.edgetoedge.MainViewMvc
import com.oliverspryn.android.edgetoedge.MainViewMvcImpl
import com.oliverspryn.android.edgetoedge.details.DetailsViewMvc
import com.oliverspryn.android.edgetoedge.details.DetailsViewMvcImpl
import com.oliverspryn.android.edgetoedge.home.HomeViewMvc
import com.oliverspryn.android.edgetoedge.home.HomeViewMvcImpl
import com.oliverspryn.android.edgetoedge.photos.PhotosViewMvc
import com.oliverspryn.android.edgetoedge.photos.PhotosViewMvcImpl
import com.oliverspryn.android.edgetoedge.photos.photolistitem.PhotoListItemViewMvc
import com.oliverspryn.android.edgetoedge.photos.photolistitem.PhotoListItemViewMvcImpl
import com.oliverspryn.android.edgetoedge.wrappers.PicassoForwarder
import com.oliverspryn.android.edgetoedge.wrappers.android.LinearLayoutManagerFactory
import javax.inject.Inject

class ViewMvcFactory @Inject constructor(
    private val layoutInflater: LayoutInflater
) {

    fun getDetailsViewMvc(
        parent: ViewGroup?,
        picassoForwarder: PicassoForwarder
    ): DetailsViewMvc = DetailsViewMvcImpl(
        inflater = layoutInflater,
        parent = parent,
        picassoForwarder = picassoForwarder
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
        parent: ViewGroup?,
        picassoForwarder: PicassoForwarder
    ): PhotoListItemViewMvc = PhotoListItemViewMvcImpl(
        inflater = layoutInflater,
        parent = parent,
        picassoForwarder = picassoForwarder
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
