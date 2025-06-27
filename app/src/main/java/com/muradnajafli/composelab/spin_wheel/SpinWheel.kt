package com.muradnajafli.composelab.spin_wheel

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.muradnajafli.composelab.R
import kotlinx.coroutines.delay
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun <T> SpinWheel(
    modifier: Modifier = Modifier,
    sections: Map<Color, T>,
    onResult: (Pair<Color, T>) -> Unit,
    initialSpinIndex: Int = 0,
    spinTimes: Int,
    wheelSize: Dp = 300.dp,
    borderSize: Dp = 16.dp,
    centerSize: Dp = 40.dp,
    iconSizeDp: Dp = 20.dp,
    spinDelay: Long = 150L,
    centerBrush: Brush,
    borderBrush: Brush,
    isSpinning: Boolean,
    onSpinning: (Boolean) -> Unit = {},
    backgroundColor: Color = Color.LightGray,
    iconVector: ImageVector = ImageVector.vectorResource(R.drawable.ic_gift),
    iconTint: Color = Color.White
) {
    val sectionList = sections.entries.toList()
    val sectionCount = sectionList.size
    val sectionAngle = 360f / sectionCount

    val iconPainter = rememberVectorPainter(image = iconVector)

    val iconSize = with(LocalDensity.current) { iconSizeDp.toPx() }

    var spinIndex by remember { mutableIntStateOf(initialSpinIndex) }

    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            repeat(spinTimes) { i ->
                spinIndex++
                val progress = i.toFloat() / spinTimes
                val currentDelay = spinDelay * (1 + 4 * progress * progress)
                delay(currentDelay.toLong())
            }
            val resultEntry = sectionList[spinIndex % sectionCount]
            onResult(resultEntry.key to resultEntry.value)
            onSpinning(false)
        }
    }

    Box(
        modifier = modifier
            .size(wheelSize)
            .clip(CircleShape)
            .border(borderSize, borderBrush, CircleShape)
            .background(backgroundColor)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            repeat(sections.size) { index ->
                val startAngle = index * sectionAngle
                val centerAngle = startAngle + sectionAngle / 2
                val currentIndex = spinIndex % sectionCount

                val entry = sectionList[index]
                val color = entry.key
                drawArc(
                    color = color,
                    size = size,
                    startAngle = startAngle,
                    sweepAngle = sectionAngle,
                    topLeft = Offset.Zero,
                    alpha = if (index == currentIndex.toString().toInt()) 1f else 0.5f,
                    useCenter = true
                )

                // Calculate icon position
                val radius = size.minDimension / 3
                val angleInRadians = centerAngle * PI / 180
                val iconX = size.width / 2 + radius * cos(angleInRadians) - iconSize / 2
                val iconY = size.height / 2 + radius * sin(angleInRadians) - iconSize / 2

                translate(iconX.toFloat(), iconY.toFloat()) {
                    rotate(
                        degrees = centerAngle + 90f,
                        pivot = Offset(iconSize / 2, iconSize / 2)
                    ) {
                        with(iconPainter) {
                            draw(
                                size = Size(iconSize, iconSize),
                                colorFilter = ColorFilter.tint(
                                    color = iconTint.copy(
                                        if (index == currentIndex) 1f else 0.5f
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .size(centerSize)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(
                    brush = centerBrush
                )
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SpinWheelPrev() {
    SpinWheel(
        onResult = {
            val color = it.first
            val value = it.second
            println("Selected Color: ${color}, Section: $value")
        },
        sections = mapOf(
            Color(0xFFe8515d) to "Section 1",
            Color(0xFFe360ef) to "Section 2",
            Color(0xFF785bf3) to "Section 3",
            Color(0xFF4981f5) to "Section 4",
            Color(0xFF97d66b) to "Section 5",
            Color(0xFF71c3ea) to "Section 6",
            Color(0xFF97d66b) to "Section 7",
            Color(0xFFed9f55) to "Section 8"
        ),
        borderBrush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF896BF4),
                Color(0xFFDF5FF3),
                Color(0xFF7EE5F0),
                Color(0xFF896BF4)
            ),
            start = Offset(0f, 0f),
            end = Offset(600f, 600f),
        ),
        isSpinning = false,
        centerBrush = Brush.sweepGradient(
            colors = listOf(
                Color(0xFF4f49ef),
                Color(0xFF8925f5),
                Color(0xFF4f49ef),
                Color(0xFF8925f5),
                Color(0xFF4f49ef),
                Color(0xFF8925f5),
                Color(0xFF4f49ef)
            ),
        ),
        spinTimes = 15
    )
}