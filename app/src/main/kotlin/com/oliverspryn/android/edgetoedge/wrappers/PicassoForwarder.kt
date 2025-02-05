package com.oliverspryn.android.edgetoedge.wrappers

import com.squareup.picasso.Picasso
import javax.inject.Inject

class PicassoForwarder @Inject constructor() {
    fun get(): Picasso = Picasso.get()
}
