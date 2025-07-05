package com.oliverspryn.android.quicke2e.sample.wrappers.android

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class ToastForwarder @Inject constructor() {
    fun makeText(
        context: Context,
        text: CharSequence,
        duration: Int
    ): Toast? = Toast.makeText(
        context,
        text,
        duration
    )
}
