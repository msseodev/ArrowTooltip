package com.msseo.android.tooltip

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.msseo.android.arrowtooltip.ArrowTooltip
import com.msseo.android.arrowtooltip.ArrowTooltipAlignment
import com.msseo.android.arrowtooltip.ArrowTooltipProperties
import com.msseo.android.arrowtooltip.TooltipShape
import com.msseo.android.tooltip.ui.theme.ArrowTooltipTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipScreen() {
    var profileTooltipVisible by remember { mutableStateOf(false) }
    var largeTooltipVisible by remember { mutableStateOf(false) }
    var profileTooltipPosition by remember { mutableStateOf(TooltipShape.ArrowPosition.Bottom) }

    LaunchedEffect(profileTooltipVisible) {
        while(profileTooltipVisible) {
            delay(2000)
            profileTooltipPosition = when(profileTooltipPosition) {
                TooltipShape.ArrowPosition.Bottom -> TooltipShape.ArrowPosition.Left
                TooltipShape.ArrowPosition.Left -> TooltipShape.ArrowPosition.Top
                TooltipShape.ArrowPosition.Top -> TooltipShape.ArrowPosition.Right
                TooltipShape.ArrowPosition.Right -> TooltipShape.ArrowPosition.Bottom
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(50.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.header),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )

                    ArrowTooltip(
                        modifier = Modifier.padding(top = 30.dp),
                        visible = profileTooltipVisible,
                        tooltipContent = {
                            Text("Set your profile image!")
                        },
                        tooltipContainerColor = generateRandomColor(),
                        tooltipShape = TooltipShape(
                            cornerRadius = 15.dp,
                            arrowSize = 8.dp,
                            arrowPosition = profileTooltipPosition
                        )
                    ) {
                        Image(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable { profileTooltipVisible = !profileTooltipVisible }
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = CircleShape,
                                )
                                .background(Color.LightGray, CircleShape)
                                .padding(4.dp),
                            imageVector = Icons.Default.Person,
                            contentDescription = "User profile image",
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))
            ArrowTooltip(
                visible = largeTooltipVisible,
                tooltipContent = {
                    Text(
                        text = stringResource(R.string.long_text),
                    )
                },
                tooltipContainerColor = Color.Magenta,
                properties = ArrowTooltipProperties(
                    spacingBetweenTooltipAndAnchor = 2.dp,
                    tooltipAlignment = ArrowTooltipAlignment.AnchorEnd
                ),
            ) {
                Column {
                    repeat(20) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { largeTooltipVisible = !largeTooltipVisible }
                                .background(color = Color.LightGray)
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "Sample ($it)"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun TooltipScreenPreview() {
    ArrowTooltipTheme {
        TooltipScreen()
    }
}