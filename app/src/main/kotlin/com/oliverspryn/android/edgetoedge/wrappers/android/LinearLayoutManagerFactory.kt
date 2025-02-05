package com.oliverspryn.android.edgetoedge.wrappers.android

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import javax.inject.Inject

class LinearLayoutManagerFactory @Inject constructor() {
    fun newInstance(context: Context) = LinearLayoutManager(context)
}
