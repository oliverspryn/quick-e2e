package com.oliverspryn.android.quicke2e.sample.mvc

interface ObservableViewMvc<Listener> : ViewMvc {
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
}
