package com.oliverspryn.android.edgetoedge.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oliverspryn.android.edgetoedge.mvc.ViewMvcFactory
import com.oliverspryn.android.edgetoedge.wrappers.PicassoForwarder
import com.oliverspryn.android.edgetoedge.wrappers.navigation.NavArgsForwarder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    @Inject
    lateinit var detailsController: DetailsController

    @Inject
    lateinit var navArgsForwarder: NavArgsForwarder

    @Inject
    lateinit var picassoForwarder: PicassoForwarder

    @Inject
    lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewMvc = viewMvcFactory.getDetailsViewMvc(container, picassoForwarder)
        detailsController.viewMvc = viewMvc

        arguments?.let {
            val deseralizedArgs = navArgsForwarder.detailsFragmentArgs(it)
            detailsController.onCreateView(deseralizedArgs.photo)
        }

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        detailsController.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        detailsController.onDestroy()
    }
}
