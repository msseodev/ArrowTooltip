package com.msseo.android.arrowtooltip

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.window.SecureFlagPolicy

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

enum class ArrowTooltipAlignment {
    AnchorStart,
    AnchorEnd,
    AnchorCenter
}