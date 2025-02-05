package com.oliverspryn.android.edgetoedge.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.oliverspryn.android.edgetoedge.R
import com.oliverspryn.android.edgetoedge.mvc.BaseViewMvc
import com.oliverspryn.android.edgetoedge.wrappers.android.LinearLayoutManagerFactory

class PhotosViewMvcImpl(
    inflater: LayoutInflater,
    linearLayoutManagerFactory: LinearLayoutManagerFactory,
    parent: ViewGroup?
) : BaseViewMvc(), PhotosViewMvc {

    @VisibleForTesting
    val photoList: RecyclerView

    init {
        rootView = inflater.inflate(R.layout.photos_fragment, parent, false)

        photoList = rootView.findViewById(R.id.photo_list)
        photoList.layoutManager = linearLayoutManagerFactory.newInstance(context)
    }

    // region PhotosViewMvc

    override fun setAdapter(adapter: PhotosAdapter) {
        photoList.adapter = adapter
    }

    // endregion
}
