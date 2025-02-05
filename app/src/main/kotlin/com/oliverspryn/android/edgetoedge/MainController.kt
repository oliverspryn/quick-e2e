package com.oliverspryn.android.edgetoedge

import android.content.Context
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.oliverspryn.android.edgetoedge.navigation.navigators.CustomFragmentNavigator
import dagger.Lazy
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class MainController @Inject constructor(
    @ActivityContext private val context: Context,
    private val navController: Lazy<NavController>
) : NavController.OnDestinationChangedListener {

    lateinit var viewMvc: MainViewMvc

    fun onCreate() {
        navController.get().addOnDestinationChangedListener(this)
    }

    fun onDestroy() {
        navController.get().removeOnDestinationChangedListener(this)
    }

    // region NavController.OnDestinationChangedListener

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val customFragment = destination as? CustomFragmentNavigator.Destination ?: return

        val showTabs = customFragment.showTabs
        val showToolbar = customFragment.showToolbar
        val title = customFragment.label?.toString() ?: context.getString(R.string.app_name)

        viewMvc.setTitle(title)
        viewMvc.showTabs(showTabs)
        viewMvc.showToolbar(showToolbar)
    }

    // endregion
}