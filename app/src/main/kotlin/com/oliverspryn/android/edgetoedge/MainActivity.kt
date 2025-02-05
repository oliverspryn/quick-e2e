package com.oliverspryn.android.edgetoedge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oliverspryn.android.edgetoedge.mvc.ViewMvcFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainController: MainController

    @Inject
    lateinit var viewMvcFactory: ViewMvcFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewMvc = viewMvcFactory.getMainViewMvc(parent = null)
        setContentView(viewMvc.rootView)

        mainController.viewMvc = viewMvc
        mainController.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainController.onDestroy()
    }
}
