package com.oliverspryn.android.quicke2e.sample.photos

import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvc

interface PhotosViewMvc : ViewMvc {
    fun setAdapter(adapter: PhotosAdapter)
}
