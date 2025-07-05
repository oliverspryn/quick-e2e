package com.oliverspryn.android.quicke2e.sample.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.oliverspryn.android.quicke2e.sample.R
import com.oliverspryn.android.quicke2e.applySideInsets
import com.oliverspryn.android.quicke2e.sample.mvc.BaseObservableViewMvc

class HomeViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?,
) : BaseObservableViewMvc<HomeViewMvc.Listener>(),
    HomeViewMvc, View.OnClickListener {

    private val firstPlaceCard: MaterialCardView
    private val photoGalleryButton: MaterialButton
    private val secondPlaceCard: MaterialCardView

    init {
        rootView = inflater.inflate(R.layout.home_fragment, parent, false)

        firstPlaceCard = findViewById(R.id.first_place)
        photoGalleryButton = findViewById(R.id.photo_gallery)
        secondPlaceCard = findViewById(R.id.second_place)

        photoGalleryButton.setOnClickListener(this)

        photoGalleryButton.applySideInsets()
        firstPlaceCard.applySideInsets()
        secondPlaceCard.applySideInsets()
    }

    override fun onClick(view: View?) {
        listeners.forEach {
            when (view) {
                photoGalleryButton -> it.onGoToListTapped()
            }
        }
    }
}
