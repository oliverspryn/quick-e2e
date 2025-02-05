package com.oliverspryn.android.edgetoedge.wrappers.android

import android.net.Uri
import javax.inject.Inject

class UriForwarder @Inject constructor() {
    fun parse(uriString: String): Uri = Uri.parse(uriString)
}
