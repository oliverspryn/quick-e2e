package com.oliverspryn.android.quicke2e.sample.photos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oliverspryn.android.quicke2e.sample.mvc.ViewMvcFactory
import com.oliverspryn.android.quicke2e.sample.photos.photolistitem.PhotoListItemViewMvc

class PhotosAdapter(
    private val listener: PhotoListItemViewMvc.Listener,
    private var photos: List<PhotosModel>,
    private val viewMvcFactory: ViewMvcFactory
) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    inner class ViewHolder(
        val viewMvc: PhotoListItemViewMvc
    ) : RecyclerView.ViewHolder(viewMvc.rootView)

    init {
        photos = emptyList()
    }

    // region RecyclerView.Adapter

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewMvc.bindPhoto(photos[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewMvc = viewMvcFactory.getPhotoListItemViewMvc(parent)
        viewMvc.registerListener(listener)

        return ViewHolder(viewMvc)
    }

    override fun getItemCount() = photos.size

    // endregion

    fun updatePhotos(photos: List<PhotosModel>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}
