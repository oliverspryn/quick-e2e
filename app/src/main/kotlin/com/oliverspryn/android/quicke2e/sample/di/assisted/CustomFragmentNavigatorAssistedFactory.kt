package com.oliverspryn.android.quicke2e.sample.di.assisted

import androidx.fragment.app.FragmentManager
import com.oliverspryn.android.quicke2e.sample.navigation.navigators.CustomFragmentNavigator
import dagger.assisted.AssistedFactory

@AssistedFactory
interface CustomFragmentNavigatorAssistedFactory {
    fun newInstance(
        fragmentManager: FragmentManager,
        containerId: Int
    ): CustomFragmentNavigator
}
