package com.oliverspryn.android.quicke2e.sample.photos.photolistitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import coil3.load
import coil3.request.error
import coil3.request.placeholder
import com.oliverspryn.android.quicke2e.sample.R
import com.oliverspryn.android.quicke2e.sample.mvc.BaseObservableViewMvc
import com.oliverspryn.android.quicke2e.sample.photos.PhotosModel

class PhotoListItemViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
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

        photo.scaleType = ImageView.ScaleType.FIT_XY
        photoId.text = photoModel.id.toString()
        title.text = photoModel.title

        photo
            .load(photoModel.thumbnailUrl) {
                error(android.R.color.darker_gray)
                placeholder(android.R.color.darker_gray)
            }
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
