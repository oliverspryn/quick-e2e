package com.oliverspryn.android.edgetoedge

import com.oliverspryn.android.edgetoedge.mvc.ViewMvc

interface MainViewMvc : ViewMvc {
    fun setTitle(title: String)
    fun showTabs(visible: Boolean)
    fun showToolbar(visible: Boolean)
}
