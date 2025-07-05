package com.oliverspryn.android.quicke2e.sample.home

import com.oliverspryn.android.quicke2e.sample.mvc.ObservableViewMvc

interface HomeViewMvc : ObservableViewMvc<HomeViewMvc.Listener> {
    interface Listener {
        fun onGoToListTapped()
    }
}
