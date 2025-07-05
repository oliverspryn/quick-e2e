package com.oliverspryn.android.quicke2e.sample.details

import com.oliverspryn.android.quicke2e.sample.mvc.ObservableViewMvc

interface DetailsViewMvc : ObservableViewMvc<DetailsViewMvc.Listener> {
    interface Listener {
        fun onPreviewTap(uri: String)
    }

    fun setPhoto(uri: String)
    fun setTitle(titleText: String)
}
