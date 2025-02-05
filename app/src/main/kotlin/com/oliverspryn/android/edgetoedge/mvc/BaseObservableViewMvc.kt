package com.oliverspryn.android.edgetoedge.mvc

import java.util.Collections
import kotlin.collections.ArrayList

abstract class BaseObservableViewMvc<Listener> : BaseViewMvc(), ObservableViewMvc<Listener> {
    private val mListeners = ArrayList<Listener>()

    val listeners: List<Listener>
        get() = Collections.unmodifiableList(mListeners)

    override fun registerListener(listener: Listener) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: Listener) {
        mListeners.remove(listener)
    }
}
