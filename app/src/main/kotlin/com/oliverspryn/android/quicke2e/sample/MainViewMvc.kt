package com.oliverspryn.android.quicke2e.sample

import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvc

interface MainViewMvc : ViewMvc {
    fun setTitle(title: String)
    fun showTabs(visible: Boolean)
    fun showToolbar(visible: Boolean)
}
