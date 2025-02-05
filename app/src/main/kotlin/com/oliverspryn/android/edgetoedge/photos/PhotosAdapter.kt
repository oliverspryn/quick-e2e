package com.oliverspryn.android.edgetoedge.photos

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oliverspryn.android.edgetoedge.photos.photolistitem.PhotoListItemViewMvc
import com.oliverspryn.android.edgetoedge.mvc.ViewMvcFactory
import com.oliverspryn.android.edgetoedge.wrappers.PicassoForwarder

class PhotosAdapter(
    private val listener: PhotoListItemViewMvc.Listener,
    private var photos: List<PhotosModel>,
    private val picassoForwarder: PicassoForwarder,
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
        val viewMvc = viewMvcFactory.getPhotoListItemViewMvc(parent, picassoForwarder)
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
