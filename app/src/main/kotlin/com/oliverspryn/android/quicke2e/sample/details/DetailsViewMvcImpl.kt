package com.oliverspryn.android.quicke2e.sample.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import coil3.load
import coil3.request.error
import com.google.android.material.button.MaterialButton
import com.oliverspryn.android.quicke2e.sample.R
import com.oliverspryn.android.quicke2e.applyBottomAndSideInsets
import com.oliverspryn.android.quicke2e.applySideInsets
import com.oliverspryn.android.quicke2e.sample.mvc.BaseObservableViewMvc
import coil3.request.placeholder

class DetailsViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseObservableViewMvc<DetailsViewMvc.Listener>(),
    DetailsViewMvc,
    View.OnClickListener {

    @VisibleForTesting
    val button: MaterialButton

    @VisibleForTesting
    var cachedUri: String? = null

    @VisibleForTesting
    val photo: AppCompatImageView

    @VisibleForTesting
    val title: AppCompatTextView

    init {
        rootView = inflater.inflate(R.layout.details_fragment, parent, false)

        button = findViewById(R.id.view_online_button)
        photo = findViewById(R.id.photo_preview)
        title = findViewById(R.id.full_title)

        button.setOnClickListener(this)

        photo.applySideInsets()
        title.applySideInsets()
        button.applyBottomAndSideInsets()
    }

    // region DetailsViewMvc

    override fun setPhoto(uri: String) {
        cachedUri = uri
        photo.scaleType = ImageView.ScaleType.FIT_XY

        photo
            .load(uri) {
                error(android.R.color.darker_gray)
                placeholder(android.R.color.darker_gray)
            }
    }

    override fun setTitle(titleText: String) {
        title.text = titleText
    }

    // endregion

    // region View.OnClickListener

    override fun onClick(view: View?) {
        cachedUri?.let { uri ->
            listeners.forEach { it.onPreviewTap(uri) }
        }
    }

    // endregion
}
