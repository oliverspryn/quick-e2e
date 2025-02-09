package com.oliverspryn.android.edgetoedge.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.button.MaterialButton
import com.oliverspryn.android.edgetoedge.R
import com.oliverspryn.android.edgetoedge.extensions.applyBottomAndSideInsets
import com.oliverspryn.android.edgetoedge.extensions.applySideInsets
import com.oliverspryn.android.edgetoedge.mvc.BaseObservableViewMvc
import com.oliverspryn.android.edgetoedge.wrappers.PicassoForwarder

class DetailsViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
    private val picassoForwarder: PicassoForwarder
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

        picassoForwarder
            .get()
            .load(uri)
            .placeholder(android.R.color.darker_gray)
            .fit()
            .into(photo)
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
