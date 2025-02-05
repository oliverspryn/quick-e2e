package com.oliverspryn.android.edgetoedge.home

import androidx.navigation.NavController
import com.oliverspryn.android.edgetoedge.R
import javax.inject.Inject

class HomeController @Inject constructor(
    private val navController: NavController
) : HomeViewMvc.Listener {

    var viewMvc: HomeViewMvc? = null

    fun onStart() {
        viewMvc?.registerListener(listener = this)
    }

    fun onDestroy() {
        viewMvc?.unregisterListener(listener = this)
    }

    // region HomeViewMvc.Listener

    override fun onGoToListTapped() {
        navController.navigate(R.id.action_home_fragment_to_photos_fragment)
    }

    // endregion
}
