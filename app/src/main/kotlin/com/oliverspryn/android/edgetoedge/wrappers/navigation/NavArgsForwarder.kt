package com.oliverspryn.android.edgetoedge.wrappers.navigation

import android.os.Bundle
import com.oliverspryn.android.edgetoedge.details.DetailsFragmentArgs
import javax.inject.Inject

class NavArgsForwarder @Inject constructor() {
    fun detailsFragmentArgs(bundle: Bundle) = DetailsFragmentArgs.fromBundle(bundle)
}
