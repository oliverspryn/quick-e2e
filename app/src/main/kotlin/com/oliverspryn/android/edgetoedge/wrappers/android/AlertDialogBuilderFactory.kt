package com.oliverspryn.android.edgetoedge.wrappers.android

import android.content.Context
import androidx.appcompat.app.AlertDialog
import javax.inject.Inject

class AlertDialogBuilderFactory @Inject constructor() {
    fun newInstance(context: Context) = AlertDialog.Builder(context)
}
