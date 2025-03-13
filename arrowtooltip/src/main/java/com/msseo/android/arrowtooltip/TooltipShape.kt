package com.msseo.android.arrowtooltip

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 * Data class representing the shape of the tooltip, including the corner radius, arrow size, and arrow position.
 *
 * @property cornerRadius The radius of the corners of the tooltip.
 * @property arrowSize The size of the arrow.
 * @property arrowPosition The position of the arrow relative to the tooltip.
 */
data class TooltipShape(
    val cornerRadius: Dp = 12.dp,
    val arrowSize: Dp = 8.dp,
    val arrowPosition: ArrowPosition = ArrowPosition.Bottom,
) {
    enum class ArrowPosition {
        Top,
        Bottom,
        Left,
        Right
    }
}

internal class ArrowTooltipShape(
    private val cornerRadius: Dp = 12.dp,
    val arrowSize: Dp = 8.dp,
    val arrowPosition: TooltipShape.ArrowPosition = TooltipShape.ArrowPosition.Bottom,
    private val arrowOffset: Float = 0f,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline = Outline.Generic(Path().apply {
        with(density) {
            val radius = cornerRadius.toPx()
            val arrow = arrowSize.toPx()

            // Draw the tooltip rectangle
            val rect = when (arrowPosition) {
                TooltipShape.ArrowPosition.Top -> Rect(0f, arrow, size.width, size.height)
                TooltipShape.ArrowPosition.Bottom -> Rect(0f, 0f, size.width, size.height - arrow)
                TooltipShape.ArrowPosition.Left -> Rect(arrow, 0f, size.width, size.height)
                TooltipShape.ArrowPosition.Right -> Rect(0f, 0f, size.width - arrow, size.height)
            }
            addRoundRect(RoundRect(rect, radius, radius))

            // Draw the arrow
            val verticalArrowXPosition = if(arrowOffset <= 0) {
                size.width / 2
            } else {
                arrowOffset.coerceIn(minimumValue = arrow, maximumValue = size.width - arrow)
            }
            val horizontalArrowYPosition = if(arrowOffset <= 0) {
                size.height / 2
            } else {
                arrowOffset.coerceIn(minimumValue = arrow, maximumValue = size.height - arrow)
            }

            when (arrowPosition) {
                TooltipShape.ArrowPosition.Top -> {
                    val arrowLeft = verticalArrowXPosition - arrow
                    moveTo(arrowLeft, arrow)
                    lineTo(verticalArrowXPosition, 0f)
                    lineTo(verticalArrowXPosition + arrow, arrow)
                    close()

                    // Draw rectangle below the arrow to cover the rounded corner
                    addRect(
                        Rect(
                            left = arrowLeft,
                            top = arrow,
                            right = arrowLeft + radius,
                            bottom = arrow + radius
                        )
                    )
                }
                TooltipShape.ArrowPosition.Bottom -> {
                    val arrowLeft = verticalArrowXPosition - arrow
                    moveTo(arrowLeft, size.height - arrow)
                    lineTo(verticalArrowXPosition, size.height)
                    lineTo(verticalArrowXPosition + arrow, size.height - arrow)
                    close()

                    // Draw rectangle below the arrow to cover the rounded corner
                    addRect(
                        Rect(
                            left = arrowLeft,
                            top = size.height - arrow - radius,
                            right = arrowLeft + arrow + radius,
                            bottom = size.height - arrow
                        )
                    )
                }

                TooltipShape.ArrowPosition.Left -> {
                    moveTo(arrow, horizontalArrowYPosition - arrow)
                    lineTo(0f, horizontalArrowYPosition)
                    lineTo(arrow, horizontalArrowYPosition + arrow)
                    close()

                    // Draw rectangle below the arrow to cover the rounded corner
                    addRect(
                        Rect(
                            left = arrow,
                            top = horizontalArrowYPosition - arrow,
                            right = arrow + radius,
                            bottom = horizontalArrowYPosition + arrow
                        )
                    )
                }

                TooltipShape.ArrowPosition.Right -> {
                    moveTo(size.width - arrow, horizontalArrowYPosition - arrow)
                    lineTo(size.width, horizontalArrowYPosition)
                    lineTo(size.width - arrow, horizontalArrowYPosition + arrow)
                    close()

                    // Draw rectangle below the arrow to cover the rounded corner
                    addRect(
                        Rect(
                            left = size.width - arrow - radius,
                            top = horizontalArrowYPosition - arrow,
                            right = size.width - arrow,
                            bottom = horizontalArrowYPosition + arrow
                        )
                    )
                }
            }
        }
    })
}
