package com.oliverspryn.android.edgetoedge.mvc

interface ObservableViewMvc<Listener> : ViewMvc {
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
}
