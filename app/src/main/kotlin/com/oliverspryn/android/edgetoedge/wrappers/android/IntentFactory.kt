package com.oliverspryn.android.edgetoedge.wrappers.android

import android.content.Intent
import android.net.Uri
import javax.inject.Inject

class IntentFactory @Inject constructor() {
    fun newInstance(action: String, uri: Uri) = Intent(action, uri)
}
