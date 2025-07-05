package com.oliverspryn.android.quicke2e.sample.mvc

import android.content.Context
import android.view.View
import androidx.annotation.IdRes

abstract class BaseViewMvc : ViewMvc {
    @Suppress("LateinitUsage")
    override lateinit var rootView: View

    protected val context: Context
        get() = rootView.context

    protected fun <T : View> findViewById(@IdRes id: Int): T = rootView.findViewById(id)
}
