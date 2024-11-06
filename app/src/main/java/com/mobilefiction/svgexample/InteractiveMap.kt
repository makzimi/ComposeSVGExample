package com.mobilefiction.svgexample

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun InteractiveMap(
    stateStates: List<StateState>,
    modifier: Modifier = Modifier,
    onStateClick: (StateState) -> Unit,
) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var zoom by remember { mutableFloatStateOf(1f) }
    var angle by remember { mutableFloatStateOf(0f) }

    Canvas(modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTransformGestures(
                onGesture = { centroid, pan, gestureZoom, gestureRotate ->
                    val oldScale = zoom
                    val newScale = zoom * gestureZoom
                    offset = (offset + centroid / oldScale).rotateBy(gestureRotate) -
                            (centroid / newScale + pan / oldScale)
                    zoom = newScale
                    angle += gestureRotate
                }
            )
        }
        .graphicsLayer {
            translationX = -offset.x * zoom
            translationY = -offset.y * zoom
            scaleX = zoom
            scaleY = zoom
            rotationZ = angle
            transformOrigin = TransformOrigin(0f, 0f)
        }
        .pointerInput(Unit) {
            detectTapGestures { offset ->
                val clickedState = stateStates.firstOrNull { statePath ->
                    statePath.boundingBox.contains(offset)
                }
                clickedState?.let { onStateClick(it) }
            }
        }
    ) {
        stateStates.forEach { statePath ->
            drawPath(
                path = statePath.path,
                color = Color.White,
                style = Stroke(width = 2.dp.toPx()) // Set stroke width to desired thickness
            )

            drawPath(
                path = statePath.path,
                color = statePath.color,
                style = Fill
            )

            // For debug purpose draw bounding box
            /*
            drawRect(
                color = Color.Green,
                topLeft = statePath.boundingBox.topLeft,
                size = statePath.boundingBox.size,
                style = Stroke(width = 1.dp.toPx())
            )
            */
        }
    }
}

fun Offset.rotateBy(angle: Float): Offset {
    val angleInRadians = angle * PI / 180
    return Offset(
        (x * cos(angleInRadians) - y * sin(angleInRadians)).toFloat(),
        (x * sin(angleInRadians) + y * cos(angleInRadians)).toFloat()
    )
}
