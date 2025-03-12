package com.msseo.android.tooltip

import androidx.compose.ui.graphics.Color

fun generateDarkColor(): Color =
    Color(
        red = (0..125).random(),
        green = (0..125).random(),
        blue = (0..125).random(),
    )

fun generateLightColor(): Color =
    Color(
        red = (125..255).random(),
        green = (125..255).random(),
        blue = (125..255).random(),
    )

fun generateRandomColor(): Color =
    Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random(),
    )
