package com.oliverspryn.android.quicke2e.sample.wrappers.navigation

import android.os.Bundle
import com.oliverspryn.android.quicke2e.sample.details.DetailsFragmentArgs
import javax.inject.Inject

class NavArgsForwarder @Inject constructor() {
    fun detailsFragmentArgs(bundle: Bundle) = DetailsFragmentArgs.fromBundle(bundle)
}
