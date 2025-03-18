# ArrowTooltip
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 주요 기능
- 간편한 Composable 함수: ArrowTooltip Composable 함수를 사용하여 툴팁을 원하는 UI 요소에 쉽게 추가할 수 있습니다.
- 화살표 모양 커스터마이징: 툴팁 화살표의 위치 (상, 하, 좌, 우) 및 정렬 (Anchor 시작, 중앙, 끝)을 자유롭게 설정하여 툴팁의 위치를 정밀하게 조정할 수 있습니다.
- 다양한 스타일 옵션: 툴팁 컨테이너 색상, 둥근 모서리, 화살표 크기, 툴팁과 앵커 사이의 간격 등 다양한 스타일 속성을 커스터마이징하여 앱 디자인에 맞는 툴팁을 만들 수 있습니다.

## 시작하기

```kotlin
dependencies {
    implementation("io.github.msseodev:arrowtooltip:0.0.2")
}
```

## 사용 방법
visible 의 값에 따라 tooltip 을 보여주거나 숨길수 있습니다.
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

### 툴팁 위치 및 화살표 모양 설정
TooltipShape 및 ArrowTooltipProperties 를 사용하여 툴팁의 화살표 위치와 정렬을 커스터마이징할 수 있습니다.

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
> ArrowPosition 과 tooltip 의 위치는 서로 반대입니다.  
> ArrowPosition 이 Top 이라면 tooltip 은 anchor 아래에 위치하고, Left 이라면 tooltip 은 anchor 오른쪽에 위치합니다.
