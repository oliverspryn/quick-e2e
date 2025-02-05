package com.oliverspryn.android.edgetoedge.photos

import com.oliverspryn.android.edgetoedge.mvc.ViewMvc

interface PhotosViewMvc : ViewMvc {
    fun setAdapter(adapter: PhotosAdapter)
}
