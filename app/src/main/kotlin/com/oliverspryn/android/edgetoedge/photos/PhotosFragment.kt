package com.oliverspryn.android.edgetoedge.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oliverspryn.android.edgetoedge.mvc.ViewMvcFactory
import com.oliverspryn.android.edgetoedge.wrappers.android.LinearLayoutManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PhotosFragment : Fragment() {

    @Inject
    lateinit var linearLayoutManagerFactory: LinearLayoutManagerFactory

    @Inject
    lateinit var photosController: PhotosController

    @Inject
    lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewMvc = viewMvcFactory.getPhotosViewMvc(linearLayoutManagerFactory, container)
        photosController.viewMvc = viewMvc

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        photosController.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        photosController.onDestroy()
    }
}
