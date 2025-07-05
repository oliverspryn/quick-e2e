package com.oliverspryn.android.quicke2e.sample.navigation.navigators

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator
import com.oliverspryn.android.quicke2e.sample.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext

@Navigator.Name("custom-fragment")
class CustomFragmentNavigator @AssistedInject constructor(
    @Assisted containerId: Int,
    @ActivityContext context: Context,
    @Assisted fragmentManager: FragmentManager
) : FragmentNavigator(context, fragmentManager, containerId) {

    override fun createDestination() = Destination(this)

    @NavDestination.ClassType(Fragment::class)
    class Destination(navigator: FragmentNavigator) : FragmentNavigator.Destination(navigator) {

        var showTabs = true
        var showToolbar = true

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            context.withStyledAttributes(attrs, R.styleable.CustomFragmentNavigator, 0, 0) {
                showTabs = getBoolean(R.styleable.CustomFragmentNavigator_showTabs, true)
                showToolbar = getBoolean(R.styleable.CustomFragmentNavigator_showToolbar, true)
            }
        }
    }
}
