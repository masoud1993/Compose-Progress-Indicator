package com.masa.composetest

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masa.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomProgressIndicator() {

    val size = 70.dp
    val thickness = 4.5.dp

    val coroutineScope = rememberCoroutineScope()
    var job : Job? by remember {
        mutableStateOf(null)
    }

    var started by remember {
        mutableStateOf(false)
    }

    var progress by remember {
        mutableStateOf(0F)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    val height by animateDpAsState(
        if (started) 0.dp else size,
        animationSpec = tween(300)
    )

    val crossAlignment by animateAlignmentAsState(
        targetAlignment = if (started) Alignment.Center else Alignment.BottomCenter
    )

    val crossPadding by animateDpAsState(
        if (started) 0.dp else 14.dp,
        animationSpec = tween(300, delayMillis = 500)
    )

    Box(
        modifier = Modifier
            .size(size)
            .clipToBounds()
            .clip(CircleShape)
            .background(Color(0xFF021AEE))
            .padding(5.dp)
            .clickable {
                if (progress == 0F) {
                    job = coroutineScope.launch {
                        started = true
                        while (progress != 1F) {
                            delay(1000)
                            progress += 0.3F
                        }
                    }
                } else {
                    started = false
                    job?.cancel()
                    progress = 0F
                }
            }
    ) {

        CircularProgressIndicator(
            progress = animatedProgress,
            color = Color.White,
            strokeWidth = thickness,
            modifier = Modifier.size(size),
            strokeCap = StrokeCap.Round
        )

        Box(
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .padding(end = crossPadding)
                    .rotate(-45F)
                    .size(thickness, 20.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .align(crossAlignment)
            )

            Box(
                modifier = Modifier
                    .padding(start = crossPadding)
                    .rotate(45F)
                    .size(thickness, 20.dp)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .align(crossAlignment)
            )
            Box(
                modifier = Modifier
                    .padding(bottom = 1.dp)
                    .size(thickness, height)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
                    .align(Alignment.BottomCenter)
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        CustomProgressIndicator()
    }
}

@Composable
fun animateAlignmentAsState(
    targetAlignment: Alignment,
): State<Alignment> {
    val biased = targetAlignment as BiasAlignment
    val horizontal by animateFloatAsState(biased.horizontalBias)
    val vertical by animateFloatAsState(biased.verticalBias)
    return derivedStateOf { BiasAlignment(horizontal, vertical) }
}