package com.msseo.android.arrowtooltip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.random.Random

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
            val verticalArrowXPosition = if (arrowOffset <= 0) {
                size.width / 2
            } else {
                arrowOffset.coerceIn(minimumValue = arrow, maximumValue = size.width - arrow)
            }
            val horizontalArrowYPosition = if (arrowOffset <= 0) {
                size.height / 2
            } else {
                arrowOffset.coerceIn(minimumValue = arrow, maximumValue = size.height - arrow)
            }

            val isArrowOnCorner = when (arrowPosition) {
                TooltipShape.ArrowPosition.Top, TooltipShape.ArrowPosition.Bottom -> verticalArrowXPosition <= arrow || verticalArrowXPosition >= size.width - arrow
                TooltipShape.ArrowPosition.Left, TooltipShape.ArrowPosition.Right -> horizontalArrowYPosition <= arrow || horizontalArrowYPosition >= size.height - arrow
            }

            when (arrowPosition) {
                TooltipShape.ArrowPosition.Top -> {
                    val arrowLeft = verticalArrowXPosition - arrow
                    if (isArrowOnCorner) {
                        val bottomLeftPoint = if (verticalArrowXPosition <= arrow) {
                            // Arrow is on left corner
                            moveTo(0f, 0f)
                            relativeLineTo(0f, arrow)
                            relativeLineTo(arrow, 0f)
                            close()
                            Offset(0f, arrow)
                        } else {
                            // Arrow is on right corner
                            moveTo(size.width, 0f)
                            relativeLineTo(0f, arrow)
                            relativeLineTo(-arrow, 0f)
                            close()
                            Offset(size.width - radius, arrow)
                        }
                        // Draw rectangle below the arrow to cover the rounded corner
                        addRect(
                            Rect(
                                left = bottomLeftPoint.x,
                                top = bottomLeftPoint.y,
                                right = bottomLeftPoint.x + radius,
                                bottom = bottomLeftPoint.y + radius
                            )
                        )
                    } else {
                        moveTo(arrowLeft, arrow)
                        lineTo(verticalArrowXPosition, 0f)
                        lineTo(verticalArrowXPosition + arrow, arrow)
                        close()
                    }
                }

                TooltipShape.ArrowPosition.Bottom -> {
                    val arrowLeft = verticalArrowXPosition - arrow
                    if (isArrowOnCorner) {
                        val topLeftPoint = if (verticalArrowXPosition <= arrow) {
                            // Arrow is on left corner
                            moveTo(0f, size.height)
                            relativeLineTo(0f, -arrow)
                            relativeLineTo(arrow, 0f)
                            close()
                            Offset(0f, size.height - arrow - radius)
                        } else {
                            // Arrow is on right corner
                            moveTo(size.width, size.height)
                            relativeLineTo(0f, -arrow)
                            relativeLineTo(-arrow, 0f)
                            close()
                            Offset(size.width - radius, size.height - arrow - radius)
                        }
                        // Draw rectangle below the arrow to cover the rounded corner
                        addRect(
                            Rect(
                                left = topLeftPoint.x,
                                top = topLeftPoint.y,
                                right = topLeftPoint.x + radius,
                                bottom = topLeftPoint.y + radius
                            )
                        )
                    } else {
                        moveTo(arrowLeft, size.height - arrow)
                        lineTo(verticalArrowXPosition, size.height)
                        lineTo(verticalArrowXPosition + arrow, size.height - arrow)
                        close()
                    }
                }

                TooltipShape.ArrowPosition.Left -> {
                    if (isArrowOnCorner) {
                        val rightTopCorner = if (horizontalArrowYPosition <= arrow) {
                            // Arrow is on top corner
                            moveTo(0f, 0f)
                            relativeLineTo(arrow, 0f)
                            relativeLineTo(0f, arrow)
                            close()
                            Offset(arrow, 0f)
                        } else {
                            // Arrow is on bottom corner
                            moveTo(0f, size.height)
                            relativeLineTo(arrow, 0f)
                            relativeLineTo(0f, -arrow)
                            close()
                            Offset(arrow, size.height - radius)
                        }

                        // Draw rectangle below the arrow to cover the rounded corner
                        addRect(
                            Rect(
                                left = rightTopCorner.x,
                                top = rightTopCorner.y,
                                right = rightTopCorner.x + radius,
                                bottom = rightTopCorner.y + radius
                            )
                        )
                    } else {
                        moveTo(arrow, horizontalArrowYPosition - arrow)
                        lineTo(0f, horizontalArrowYPosition)
                        lineTo(arrow, horizontalArrowYPosition + arrow)
                        close()
                    }
                }

                TooltipShape.ArrowPosition.Right -> {
                    if (isArrowOnCorner) {
                        val leftTopCorner = if (horizontalArrowYPosition <= arrow) {
                            // Arrow is on top corner
                            moveTo(size.width, 0f)
                            relativeLineTo(-arrow, 0f)
                            relativeLineTo(0f, arrow)
                            close()
                            Offset(size.width - arrow - radius, 0f)
                        } else {
                            // Arrow is on bottom corner
                            moveTo(size.width, size.height)
                            relativeLineTo(-arrow, 0f)
                            relativeLineTo(0f, -arrow)
                            close()
                            Offset(size.width - arrow - radius, size.height - radius)
                        }

                        // Draw rectangle below the arrow to cover the rounded corner
                        addRect(
                            Rect(
                                left = leftTopCorner.x,
                                top = leftTopCorner.y,
                                right = leftTopCorner.x + radius,
                                bottom = leftTopCorner.y + radius
                            )
                        )
                    } else {
                        moveTo(size.width - arrow, horizontalArrowYPosition - arrow)
                        lineTo(size.width, horizontalArrowYPosition)
                        lineTo(size.width - arrow, horizontalArrowYPosition + arrow)
                        close()
                    }
                }
            }
        }
    })
}

@Composable
private fun ArrowBoxes(
    modifier: Modifier = Modifier,
    arrowPosition: TooltipShape.ArrowPosition,
    text: String,
) {
    fun randomColor(): Color = Color(
        red = Random.nextInt(128, 256),
        green = Random.nextInt(128, 256),
        blue = Random.nextInt(128, 256),
    )

    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    color = randomColor(),
                    shape = ArrowTooltipShape(
                        cornerRadius = 20.dp,
                        arrowPosition = arrowPosition,
                        arrowOffset = 5000f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$text right")
        }

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(100.dp)
                .background(
                    color = randomColor(),
                    shape = ArrowTooltipShape(
                        cornerRadius = 20.dp,
                        arrowPosition = arrowPosition,
                        arrowOffset = 1f
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$text left")
        }

        Box(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(100.dp)
                .background(
                    color = randomColor(),
                    shape = ArrowTooltipShape(
                        cornerRadius = 20.dp,
                        arrowPosition = arrowPosition,
                        arrowOffset = with(LocalDensity.current) { 50.dp.toPx() }
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "$text center")
        }
    }
}

@Preview
@Composable
private fun PreviewOffsetArrowTooltip() {
    Column(modifier = Modifier.padding(16.dp)) {
        ArrowBoxes(
            modifier = Modifier.padding(vertical = 8.dp),
            arrowPosition = TooltipShape.ArrowPosition.Top,
            text = "Top"
        )
        ArrowBoxes(
            modifier = Modifier.padding(vertical = 8.dp),
            arrowPosition = TooltipShape.ArrowPosition.Bottom,
            text = "Bottom"
        )
        ArrowBoxes(
            modifier = Modifier.padding(vertical = 8.dp),
            arrowPosition = TooltipShape.ArrowPosition.Left,
            text = "Left"
        )
        ArrowBoxes(
            modifier = Modifier.padding(vertical = 8.dp),
            arrowPosition = TooltipShape.ArrowPosition.Right,
            text = "Right"
        )
    }
}
