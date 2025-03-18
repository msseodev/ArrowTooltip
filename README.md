# ArrowTooltip

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

🌏
English |
[**한국어**](https://github.com/msseodev/ArrowTooltip/blob/main/README.ko.md)

ArrowTooltip lets you implement balloon-shaped tooltips in Android Compose.

<p align="center">
    <img src="https://github.com/user-attachments/assets/6cd1d8af-b8f3-4436-aa16-4b5c03e179bf" width="450" />
</p>

## Key Features

- **Simple Composable Function:** Easily add tooltips to your desired UI elements using the `ArrowTooltip` Composable function.
- **Arrow Shape Customization:** Precisely adjust the tooltip's position by freely setting the arrow's position (top, bottom, left, right) and alignment (Anchor start, center, end).
- **Various Style Options:** Customize various style attributes such as tooltip container color, rounded corners, arrow size, and spacing between the tooltip and anchor to create tooltips that match your app's design.

## Getting Started

Add the dependency in your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("io.github.msseodev:arrowtooltip:0.0.2")
}
```

## Usage
You can show or hide the tooltip depending on the value of visible.

```kotlin
@Composable
fun BasicTooltipExample() {
    var tooltipVisible by remember { mutableStateOf(false) }

    ArrowTooltip(
        visible = tooltipVisible,
        tooltipContent = {
            Text("I am a basic arrow tooltip!")
        }
    ) {
        Button(onClick = { tooltipVisible = !tooltipVisible }) {
            Text("Show/Hide tooltip")
        }
    }
}
```

## Tooltip Position and Arrow Shape Settings
You can customize the tooltip's arrow position and alignment using TooltipShape and ArrowTooltipProperties.

```kotlin
@Composable
fun PositionedTooltipExample() {
    var tooltipVisible by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        ArrowTooltip(
            visible = tooltipVisible,
            tooltipContent = { Text("I am arrow tooltip!") },
            tooltipShape = TooltipShape(
                cornerRadius = 8.dp,
                arrowSize = 8.dp,
                arrowPosition = TooltipShape.ArrowPosition.Top
            ),
            properties = ArrowTooltipProperties(
                tooltipAlignment = ArrowTooltipAlignment.AnchorCenter
            )
        ) {
            Button(onClick = { tooltipVisible = !tooltipVisible }) {
                Text(text = "Toggle tooltip")
            }
        }
    }
}
```

> [!NOTE]
> The ArrowPosition and the tooltip's position are opposite to each other.
> If ArrowPosition is Top, the tooltip is positioned below the anchor, and if it is Left, the tooltip is positioned to the right of the anchor.
