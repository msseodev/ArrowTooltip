package com.msseo.android.arrowtooltip

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy

/**
 * Data class representing the properties for configuring the ArrowTooltip.
 *
 * @property tooltipAlignment The alignment of the tooltip relative to the anchor element.
 * @property spacingBetweenTooltipAndAnchor The spacing between the tooltip and the anchor element.
 * @property focusable Whether the popup can receive focus.
 * @property dismissOnBackPress Whether the popup should be dismissed when the back button is pressed.
 * @property dismissOnClickOutside Whether the popup should be dismissed when clicking outside of it.
 * @property securePolicy The policy for handling secure flags.
 * @property excludeFromSystemGesture Whether the popup should be excluded from system gestures.
 * @property clippingEnabled Whether clipping is enabled for the popup.
 */
data class ArrowTooltipProperties(
    val tooltipAlignment: ArrowTooltipAlignment = ArrowTooltipAlignment.AnchorCenter,
    val spacingBetweenTooltipAndAnchor: Dp = 4.dp,

    // PopupProperties
    val focusable: Boolean = false,
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = false,
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,
    val excludeFromSystemGesture: Boolean = true,
    val clippingEnabled: Boolean = false,
)

internal fun ArrowTooltipProperties.toPopupProperties(): PopupProperties =
    PopupProperties(
        focusable = focusable,
        dismissOnBackPress = dismissOnBackPress,
        dismissOnClickOutside = dismissOnClickOutside,
        securePolicy = securePolicy,
        excludeFromSystemGesture = excludeFromSystemGesture,
        clippingEnabled = clippingEnabled,
    )

/**
 * Defines possible alignments of the tooltip relative to the anchor.
 */
enum class ArrowTooltipAlignment {
    /**
     * Aligns the tooltip at the anchor's start.
     */
    AnchorStart,

    /**
     * Aligns the tooltip at the anchor's end.
     */
    AnchorEnd,

    /**
     * Aligns the tooltip at the center of the anchor.
     */
    AnchorCenter
}