package com.oliverspryn.android.quicke2e.sample.wrappers

import android.os.Bundle
import androidx.core.os.BundleCompat
import javax.inject.Inject

class BundleCompatForwarder @Inject constructor() {
    fun <Type> getParcelable(
        `in`: Bundle, key: String?, clazz: Class<Type>
    ) = BundleCompat.getParcelable(
        `in`, key, clazz
    )
}

