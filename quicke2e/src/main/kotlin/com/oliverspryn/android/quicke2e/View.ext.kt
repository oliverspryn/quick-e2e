package com.oliverspryn.android.quicke2e

import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams

/**
 * Manually applies system window insets to the outer margins of any given
 * view. Using this method to apply insets will preserve any existing
 * margins on the view and then add the system's insets on top of the
 * view's specified margins. That is to say, inset calculations are
 * additive when applying them to views which already have outer margins.
 *
 * A simple example of how to use this method is as follows:
 *
 *     view.applyEdgeToEdgeInsets { insets ->
 *         topMargin = insets.top         // `topMargin` refers to the `view`'s top margin
 *         bottomMargin = insets.bottom   // etc.
 *         leftMargin = insets.left
 *         rightMargin = insets.right
 *     }
 *
 * This approach is great for a customized approach, but there are several
 * convenience functions that you can use to address most common use cases
 * instead of having to manually apply the insets like you see above.
 *
 * These functions are as follows:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * For most use cases, it is suggested to use the convenience functions
 * above, which is simply a boilerplate wrapper around this method.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @param block The block for you to apply the insets to your view. This
 *    block will be called with the view's margins and an
 *    `InsetsAccumulator` object that contains the insets to apply to the
 *    view.
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applyEdgeToEdgeInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true,
    block: MarginLayoutParams.(InsetsAccumulator) -> Unit
) {
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, windowInsets ->
        val insets = windowInsets.getInsets(typeMask)

        val initialTop = if (view.getTag(R.id.initial_margin_top) != null) {
            view.getTag(R.id.initial_margin_top) as Int
        } else {
            view.setTag(R.id.initial_margin_top, view.marginTop)
            view.marginTop
        }

        val initialBottom = if (view.getTag(R.id.initial_margin_bottom) != null) {
            view.getTag(R.id.initial_margin_bottom) as Int
        } else {
            view.setTag(R.id.initial_margin_bottom, view.marginBottom)
            view.marginBottom
        }

        val initialLeft = if (view.getTag(R.id.initial_margin_left) != null) {
            view.getTag(R.id.initial_margin_left) as Int
        } else {
            view.setTag(R.id.initial_margin_left, view.marginLeft)
            view.marginLeft
        }

        val initialRight = if (view.getTag(R.id.initial_margin_right) != null) {
            view.getTag(R.id.initial_margin_right) as Int
        } else {
            view.setTag(R.id.initial_margin_right, view.marginRight)
            view.marginRight
        }

        val accumulator = InsetsAccumulator(
            initialTop,
            insets.top,
            initialBottom,
            insets.bottom,
            initialLeft,
            insets.left,
            initialRight,
            insets.right
        )

        view.updateLayoutParams<MarginLayoutParams> {
            apply { block(accumulator) }
        }

        if (propagateInsets) windowInsets else WindowInsetsCompat.CONSUMED
    }
}

/**
 * Applies system window insets to all of the outer margins of any given
 * view. Using this method to apply insets will preserve any existing
 * margins on the view and then add the system's insets on top of the
 * view's specified margins. That is to say, inset calculations are
 * additive when applying them to views which already have outer margins.
 *
 * Here are the insets it applies:
 *
 *                      |
 *                      |
 *                      v
 *            +===================+
 *            |                   |
 *            |                   |
 *     =====> |                   | <=====
 *            |                   |
 *            |                   |
 *            +===================+
 *                      ^
 *                      |
 *                      |
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - **This function.** [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applyAllInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    topMargin = insets.top
    bottomMargin = insets.bottom
    leftMargin = insets.left
    rightMargin = insets.right
}

/**
 * Applies system window insets to the bottom outer margin of any given
 * view. Using this method to apply insets will preserve any existing
 * margins on the view and then add the system's insets on top of the
 * view's specified margins. That is to say, inset calculations are
 * additive when applying them to views which already have outer margins.
 *
 * Here are the insets it applies:
 *
 *            +===================+
 *            |                   |
 *            |                   |
 *            |                   |
 *            |                   |
 *            |                   |
 *            +===================+
 *                      ^
 *                      |
 *                      |
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - **This function.** [View.applyBottomInsets] -- Inset the bottom of a
 *   view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applyBottomInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    bottomMargin = insets.bottom
}

/**
 * Applies system window insets to the bottom and both sides of the outer
 * margins of any given view. Using this method to apply insets will
 * preserve any existing margins on the view and then add the system's
 * insets on top of the view's specified margins. That is to say, inset
 * calculations are additive when applying them to views which already have
 * outer margins.
 *
 * Here are the insets it applies:
 *
 *            +===================+
 *            |                   |
 *            |                   |
 *     =====> |                   | <=====
 *            |                   |
 *            |                   |
 *            +===================+
 *                      ^
 *                      |
 *                      |
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - **This function.** [View.applyBottomAndSideInsets] -- Inset the
 *   bottom, start (left), and end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applyBottomAndSideInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    bottomMargin = insets.bottom
    leftMargin = insets.left
    rightMargin = insets.right
}

/**
 * Applies system window insets to both sides of the outer margins of
 * any given view. Using this method to apply insets will preserve any
 * existing margins on the view and then add the system's insets on top of
 * the view's specified margins. That is to say, inset calculations are
 * additive when applying them to views which already have outer margins.
 *
 * Here are the insets it applies:
 *
 *            +===================+
 *            |                   |
 *            |                   |
 *     =====> |                   | <=====
 *            |                   |
 *            |                   |
 *            +===================+
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - **This function.** [View.applySideInsets] -- Inset the start (left)
 *   and end (right) of a view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applySideInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    leftMargin = insets.left
    rightMargin = insets.right
}

/**
 * Applies system window insets to the top outer margin of any given view.
 * Using this method to apply insets will preserve any existing margins on
 * the view and then add the system's insets on top of the view's specified
 * margins. That is to say, inset calculations are additive when applying
 * them to views which already have outer margins.
 *
 * Here are the insets it applies:
 *
 *                      |
 *                      |
 *                      v
 *            +===================+
 *            |                   |
 *            |                   |
 *            |                   |
 *            |                   |
 *            |                   |
 *            +===================+
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - **This function.** [View.applyTopInsets] -- Inset the top of a view.
 * - [View.applyTopAndSideInsets] -- Inset the top, start (left), and end
 *   (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopAndSideInsets
 * @see InsetsAccumulator
 */
fun View.applyTopInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    topMargin = insets.top
}

/**
 * Applies system window insets to the top and both sides of the outer
 * margins of any given view. Using this method to apply insets will
 * preserve any existing margins on the view and then add the system's
 * insets on top of the view's specified margins. That is to say, inset
 * calculations are additive when applying them to views which already have
 * outer margins.
 *
 * Here are the insets it applies:
 *
 *                      |
 *                      |
 *                      v
 *            +===================+
 *            |                   |
 *            |                   |
 *     =====> |                   | <=====
 *            |                   |
 *            |                   |
 *            +===================+
 *
 * For a more detailed explanation of how this method works, see the
 * documentation for [View.applyEdgeToEdgeInsets]. Should you require
 * more customization than what this method offers, you can use
 * [View.applyEdgeToEdgeInsets] directly.
 *
 * However, it is recommended to use this family of convenience functions
 * for most use cases:
 * - [View.applyAllInsets] -- Inset all sides of a view.
 * - [View.applyBottomInsets] -- Inset the bottom of a view.
 * - [View.applyBottomAndSideInsets] -- Inset the bottom, start (left), and
 *   end (right) of a view.
 * - [View.applySideInsets] -- Inset the start (left) and end (right) of a
 *   view.
 * - [View.applyTopInsets] -- Inset the top of a view.
 * - **This function.** [View.applyTopAndSideInsets] -- Inset the top,
 *   start (left), and end (right) of a view.
 *
 * @param typeMask The type of insets to apply to the view. By default,
 *    this assumes you want to inset from system bars (navigation and
 *    status bars), display cutouts, and the IME (keyboard). You can
 *    customize this mask to only apply insets from specific sources.
 * @param propagateInsets Whether or not to propagate the insets to the
 *    view's children. If `true`, the insets will be passed to the view's
 *    children. If `false`, the insets will be consumed and not passed to
 *    the view's children. Set to `true` by default.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see InsetsAccumulator
 */
fun View.applyTopAndSideInsets(
    typeMask: Int = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout() or WindowInsetsCompat.Type.ime(),
    propagateInsets: Boolean = true
) = applyEdgeToEdgeInsets(typeMask, propagateInsets) { insets ->
    topMargin = insets.top
    leftMargin = insets.left
    rightMargin = insets.right
}

/**
 * A utility class that combines both the initial and inset margins for
 * all sides of a view, thereby allowing the caller to simply apply the
 * calculated insets without fear of it clearing out any existing margins
 * on the view.
 *
 * @param initialTop The initial top margin of the view.
 * @param insetTop The inset top margin to apply to the view.
 * @param initialBottom The initial bottom margin of the view.
 * @param insetBottom The inset bottom margin to apply to the view.
 * @param initialLeft The initial left margin of the view.
 * @param insetLeft The inset left margin to apply to the view.
 * @param initialRight The initial right margin of the view.
 * @param insetRight The inset right margin to apply to the view.
 * @constructor Creates a ready-made class with all of the necessary insets
 *    that can be applied without further checks or math to achieve the
 *    intended result.
 * @see View.applyEdgeToEdgeInsets
 * @see View.applyAllInsets
 * @see View.applyBottomInsets
 * @see View.applyBottomAndSideInsets
 * @see View.applySideInsets
 * @see View.applyTopInsets
 * @see View.applyTopAndSideInsets
 */
data class InsetsAccumulator(
    private val initialTop: Int,
    private val insetTop: Int,
    private val initialBottom: Int,
    private val insetBottom: Int,
    private val initialLeft: Int,
    private val insetLeft: Int,
    private val initialRight: Int,
    private val insetRight: Int
) {

    /**
     * The top margin to apply to the view, with both the initial margin and
     * inset margin combined.
     */
    val top: Int
        get() = initialTop + insetTop

    /**
     * The bottom margin to apply to the view, with both the initial margin and
     * inset margin combined.
     */
    val bottom: Int
        get() = initialBottom + insetBottom

    /**
     * The left margin to apply to the view, with both the initial margin and
     * inset margin combined.
     */
    val left: Int
        get() = initialLeft + insetLeft

    /**
     * The right margin to apply to the view, with both the initial margin and
     * inset margin combined.
     */
    val right: Int
        get() = initialRight + insetRight
}

