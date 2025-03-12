package com.msseo.android.arrowtooltip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup

@Composable
fun ArrowTooltip(
    modifier: Modifier = Modifier,
    visible: Boolean = true,
    properties: ArrowTooltipProperties = ArrowTooltipProperties(),
    tooltipShape: TooltipShape = TooltipShape(),
    tooltipContainerColor: Color = MaterialTheme.colorScheme.surface,
    tooltipContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    var arrowTooltipShape by remember {
        mutableStateOf(
            ArrowTooltipShape(
                cornerRadius = tooltipShape.cornerRadius,
                arrowSize = tooltipShape.arrowSize,
                arrowPosition = tooltipShape.arrowPosition
            )
        )
    }

    Box(modifier = modifier) {
        if (visible) {
            Popup(
                popupPositionProvider = ArrowTooltipPositionProvider.rememberArrowTooltipPositionProvider(
                    spacingBetweenTooltipAndAnchor = properties.spacingBetweenTooltipAndAnchor,
                    tooltipPosition = when (tooltipShape.arrowPosition) {
                        TooltipShape.ArrowPosition.Top -> ArrowTooltipPositionProvider.TooltipPosition.Below
                        TooltipShape.ArrowPosition.Bottom -> ArrowTooltipPositionProvider.TooltipPosition.Above
                        TooltipShape.ArrowPosition.Left -> ArrowTooltipPositionProvider.TooltipPosition.Right
                        TooltipShape.ArrowPosition.Right -> ArrowTooltipPositionProvider.TooltipPosition.Left
                    },
                    tooltipAlignment = properties.tooltipAlignment,
                    boundsChanged = { anchorBounds, tooltipBounds ->
                        val anchorMiddleX = anchorBounds.left + anchorBounds.width / 2
                        val anchorMiddleY = anchorBounds.top + anchorBounds.height / 2

                        arrowTooltipShape = ArrowTooltipShape(
                            cornerRadius = tooltipShape.cornerRadius,
                            arrowSize = tooltipShape.arrowSize,
                            arrowPosition = tooltipShape.arrowPosition,
                            arrowOffset = when (tooltipShape.arrowPosition) {
                                TooltipShape.ArrowPosition.Top, TooltipShape.ArrowPosition.Bottom -> {
                                    (anchorMiddleX - tooltipBounds.left).toFloat()
                                }

                                TooltipShape.ArrowPosition.Left, TooltipShape.ArrowPosition.Right -> {
                                    (anchorMiddleY - tooltipBounds.top).toFloat()
                                }
                            },
                        )
                    }
                ),
                properties = properties.toPopupProperties(),
                content = {
                    Surface(
                        shape = arrowTooltipShape,
                        color = tooltipContainerColor,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .padding(
                                    top = when (tooltipShape.arrowPosition) {
                                        TooltipShape.ArrowPosition.Top -> tooltipShape.arrowSize
                                        else -> 0.dp
                                    },
                                    bottom = when (tooltipShape.arrowPosition) {
                                        TooltipShape.ArrowPosition.Bottom -> tooltipShape.arrowSize
                                        else -> 0.dp
                                    },
                                    start = when (tooltipShape.arrowPosition) {
                                        TooltipShape.ArrowPosition.Left -> tooltipShape.arrowSize
                                        else -> 0.dp
                                    },
                                    end = when (tooltipShape.arrowPosition) {
                                        TooltipShape.ArrowPosition.Right -> tooltipShape.arrowSize
                                        else -> 0.dp
                                    }
                                )
                        ) {
                            tooltipContent()
                        }
                    }
                }
            )
        }

        content()
    }
}
