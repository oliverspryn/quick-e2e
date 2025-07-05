package com.oliverspryn.android.quicke2e.sample.navigation.navigators

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.oliverspryn.android.quicke2e.sample.R
import com.oliverspryn.android.quicke2e.sample.wrappers.BundleCompatForwarder
import com.oliverspryn.android.quicke2e.sample.wrappers.android.AlertDialogBuilderFactory
import com.oliverspryn.android.quicke2e.sample.wrappers.android.IntentFactory
import com.oliverspryn.android.quicke2e.sample.wrappers.android.UriForwarder
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

/**
 * Purpose:
 *
 *    This navigates to the default browser. If a URL is specified, it will navigate
 *    to it. If no URL is specified, it expects a URI argument to be passed to it via
 *    the nav graph.
 *
 *    In either case, if a web browser is not installed, the user is shown a dialog
 *    asking them to install a browser.
 *
 * Usage:
 *
 *    <http
 *      android:id="@+id/some_external_url"
 *      app:url="https://duckduckgo.com/"
 *      tools:layout="@layout/graph_placeholder_http" />
 *
 * Options:
 *
 *    - url (string, optional): Takes the user directly to this URL if specified
 */

@Navigator.Name("http")
class HttpNavigator @Inject constructor(
    private val alertDialogBuilderFactory: AlertDialogBuilderFactory,
    private val bundleCompatForwarder: BundleCompatForwarder,
    @param:ActivityContext private val context: Context,
    private val intentFactory: IntentFactory,
    private val uriForwarder: UriForwarder
) : Navigator<HttpNavigator.Destination>() {

    override fun createDestination() = Destination(this)

    override fun navigate(
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Extras?
    ): NavDestination? {

        val uri: Uri? = if (destination.url.isEmpty()) {
            if (args != null) {
                bundleCompatForwarder.getParcelable(
                    args,
                    "uri",
                    Uri::class.java
                )
            } else {
                null
            }
        } else {
            uriForwarder.parse(destination.url)
        }

        uri?.let {
            val intent = intentFactory.newInstance(Intent.ACTION_VIEW, it)

            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                showError()
            }
        }

        return null // Do not add to the back stack, managed by external app
    }

    override fun popBackStack() = true // Managed by external app

    private fun showError() {
        val builder = alertDialogBuilderFactory.newInstance(context)
        builder.setCancelable(true)
        builder.setMessage(R.string.browser_unavailable)

        builder.setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.cancel()
        }

        builder.create().show()
    }

    @NavDestination.ClassType(Activity::class)
    class Destination(navigator: Navigator<out NavDestination>) : NavDestination(navigator) {

        var url: String = ""

        override fun onInflate(context: Context, attrs: AttributeSet) {
            super.onInflate(context, attrs)

            context.withStyledAttributes(attrs, R.styleable.HttpNavigator, 0, 0) {
                url = getString(R.styleable.HttpNavigator_url) ?: ""
            }
        }
    }
}
