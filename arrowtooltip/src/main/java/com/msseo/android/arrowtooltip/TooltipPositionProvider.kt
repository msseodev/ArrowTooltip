package com.msseo.android.arrowtooltip

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider

object ArrowTooltipPositionProvider {

    @Composable
    fun rememberArrowTooltipPositionProvider(
        spacingBetweenTooltipAndAnchor: Dp = 4.dp,
        tooltipPosition: TooltipPosition = TooltipPosition.Below,
        tooltipAlignment: ArrowTooltipAlignment,
        boundsChanged: (IntRect, IntRect) -> Unit = { _, _ -> }
    ): PopupPositionProvider {
        val tooltipAnchorSpacing = with(LocalDensity.current) {
            spacingBetweenTooltipAndAnchor.roundToPx()
        }
        return remember(tooltipAnchorSpacing, tooltipPosition, tooltipAlignment) {
            object : PopupPositionProvider {
                override fun calculatePosition(
                    anchorBounds: IntRect,
                    windowSize: IntSize,
                    layoutDirection: LayoutDirection,
                    popupContentSize: IntSize
                ): IntOffset {
                    val x = when(tooltipPosition) {
                        TooltipPosition.Above, TooltipPosition.Below -> {
                            when(tooltipAlignment) {
                                ArrowTooltipAlignment.AnchorStart -> anchorBounds.left
                                ArrowTooltipAlignment.AnchorEnd -> anchorBounds.right - popupContentSize.width
                                ArrowTooltipAlignment.AnchorCenter -> anchorBounds.left + ((anchorBounds.width - popupContentSize.width) / 2)
                            }
                        }
                        TooltipPosition.Left -> {
                            anchorBounds.left - popupContentSize.width - tooltipAnchorSpacing
                        }
                        TooltipPosition.Right -> {
                            anchorBounds.right + tooltipAnchorSpacing
                        }
                    }

                    val y = when(tooltipPosition) {
                        TooltipPosition.Above -> {
                            anchorBounds.top - popupContentSize.height - tooltipAnchorSpacing
                        }
                        TooltipPosition.Below -> {
                            anchorBounds.bottom + tooltipAnchorSpacing
                        }
                        TooltipPosition.Left, TooltipPosition.Right -> {
                            when(tooltipAlignment) {
                                ArrowTooltipAlignment.AnchorStart -> anchorBounds.top
                                ArrowTooltipAlignment.AnchorEnd -> anchorBounds.bottom - popupContentSize.height
                                ArrowTooltipAlignment.AnchorCenter -> anchorBounds.top + ((anchorBounds.height - popupContentSize.height) / 2)
                            }
                        }
                    }

                    boundsChanged(
                        anchorBounds,
                        IntRect(
                            offset = IntOffset(x, y),
                            size = popupContentSize
                        )
                    )

                    return IntOffset(x, y)
                }
            }
        }
    }

    enum class TooltipPosition {
        Above,
        Below,
        Left,
        Right
    }

}