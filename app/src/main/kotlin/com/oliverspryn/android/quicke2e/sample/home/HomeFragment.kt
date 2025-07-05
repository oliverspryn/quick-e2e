package com.oliverspryn.android.quicke2e.sample.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvcFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    @Inject
    lateinit var homeController: HomeController

    @Inject
    lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewMvc = viewMvcFactory.getHomeViewMvc(container)
        homeController.viewMvc = viewMvc

        return viewMvc.rootView
    }

    override fun onStart() {
        super.onStart()
        homeController.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        homeController.onDestroy()
    }
}
