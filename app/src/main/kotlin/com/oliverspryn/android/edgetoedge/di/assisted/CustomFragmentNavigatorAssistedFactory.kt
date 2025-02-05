package com.oliverspryn.android.edgetoedge.di.assisted

import androidx.fragment.app.FragmentManager
import com.oliverspryn.android.edgetoedge.navigation.navigators.CustomFragmentNavigator
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CustomFragmentNavigatorAssistedFactory {
    fun newInstance(
        fragmentManager: FragmentManager,
        containerId: Int
    ): CustomFragmentNavigator
}
