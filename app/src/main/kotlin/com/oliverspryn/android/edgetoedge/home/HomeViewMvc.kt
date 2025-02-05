package com.oliverspryn.android.edgetoedge.home

import com.oliverspryn.android.edgetoedge.mvc.ObservableViewMvc

interface HomeViewMvc : ObservableViewMvc<HomeViewMvc.Listener> {
    interface Listener {
        fun onGoToListTapped()
    }
}
