package com.oliverspryn.android.edgetoedge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oliverspryn.android.edgetoedge.extensions.applySideInsets
import com.oliverspryn.android.edgetoedge.mvc.BaseViewMvc

class MainViewMvcImpl(
    inflater: LayoutInflater,
    parent: ViewGroup?
) : BaseViewMvc(), MainViewMvc {

    private val appBar: AppBarLayout
    private val root: ConstraintLayout
    private val tabs: BottomNavigationView
    private val title: TextView

    init {
        rootView = inflater.inflate(R.layout.activity_main, parent, false)

        appBar = findViewById(R.id.app_bar)
        root = findViewById(R.id.root)
        tabs = findViewById(R.id.tabs)
        title = findViewById(R.id.title)

        title.applySideInsets()
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun showTabs(visible: Boolean) {
        tabs.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showToolbar(visible: Boolean) {
        appBar.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
