package com.oliverspryn.android.edgetoedge.photos.photolistitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.oliverspryn.android.edgetoedge.R
import com.oliverspryn.android.edgetoedge.photos.PhotosModel
import com.oliverspryn.android.edgetoedge.mvc.BaseObservableViewMvc
import com.oliverspryn.android.edgetoedge.wrappers.PicassoForwarder

class PhotoListItemViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    private val picassoForwarder: PicassoForwarder
) : BaseObservableViewMvc<PhotoListItemViewMvc.Listener>(),
    PhotoListItemViewMvc,
    View.OnClickListener {

    @VisibleForTesting
    var data: PhotosModel? = null

    @VisibleForTesting
    val photo: AppCompatImageView

    @VisibleForTesting
    val photoId: AppCompatTextView

    @VisibleForTesting
    val row: ConstraintLayout

    @VisibleForTesting
    val title: AppCompatTextView

    init {
        rootView = inflater.inflate(R.layout.photo_list_item_fragment, parent, false)

        photo = rootView.findViewById(R.id.photo)
        photoId = rootView.findViewById(R.id.photo_id)
        row = rootView.findViewById(R.id.photo_row)
        title = rootView.findViewById(R.id.title)

        row.setOnClickListener(this)
    }

    // region PhotoListItemViewMvc

    override fun bindPhoto(photoModel: PhotosModel) {
        data = photoModel

        photoId.text = photoModel.id.toString()
        title.text = photoModel.title

        picassoForwarder
            .get()
            .load(photoModel.thumbnailUrl)
            .placeholder(android.R.color.darker_gray)
            .fit()
            .into(photo)
    }

    // endregion

    // region View.OnClickListener

    override fun onClick(view: View?) {
        data?.let { model ->
            listeners.forEach { it.onPhotoTap(photoModel = model) }
        }
    }

    // endregion
}
