package com.oliverspryn.android.quicke2e.sample.di.modules

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {
    @Provides
    fun provideNavController(activity: FragmentActivity): NavController {
        val navHostFragment = activity
            .supportFragmentManager
            .findFragmentByTag("navigation_host") as NavHostFragment

        return navHostFragment.navController
    }
}
