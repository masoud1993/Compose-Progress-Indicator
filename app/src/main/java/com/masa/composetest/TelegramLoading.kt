package com.masa.composetest

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.masa.composetest.ui.theme.ComposeTestTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TelegramLoading(size: Dp) {

    val coroutineScope = rememberCoroutineScope()
    var job : Job? by remember {
        mutableStateOf(null)
    }

    var progress by remember {
        mutableStateOf(0F)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    val height by animateDpAsState(
        if (progress > 0F) 0.dp else size,
        animationSpec = tween(500, delayMillis = 200)
    )
    val crossHeight by animateDpAsState(
        if (progress > 0F) 0.dp else 40.dp,
        animationSpec = tween(500, delayMillis = 200)
    )
    val closeHeight by animateDpAsState(
        if (progress == 0F) 0.dp else 40.dp,
        animationSpec = tween(500, delayMillis = 200)
    )

    Box(
        modifier = Modifier
            .size(size)
            .clipToBounds()
            .clip(CircleShape)
            .background(Color.Blue)
            .padding(10.dp)
    ) {

        CircularProgressIndicator(
            progress = animatedProgress,
            color = Color.White,
            strokeWidth = 9.dp,
            modifier = Modifier.size(size)
        )

        Box(modifier = Modifier
            .align(Alignment.Center)
            .clickable {
                job?.cancel()
                progress = 0F
            }) {
            Box(
                modifier = Modifier
                    .rotate(-45F)
                    .size(9.dp, closeHeight)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
            )

            Box(
                modifier = Modifier
                    .rotate(45F)
                    .size(9.dp, closeHeight)
                    .background(Color.White, shape = RoundedCornerShape(10.dp))
            )
        }

        Box(
            modifier = Modifier
                .size(9.dp, height)
                .background(Color.Yellow, shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
                .clickable {
                    job = coroutineScope.launch {
                        while (progress != 1F) {
                            delay(1000)
                            progress += 0.1F
                        }
                    }
                }
        )
        Box(
            modifier = Modifier
                .padding(end = 20.dp)
                .rotate(-45F)
                .size(9.dp, crossHeight)
                .background(Color.Red, shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
        )

        Box(
            modifier = Modifier
                .padding(start = 20.dp)
                .rotate(45F)
                .size(9.dp, crossHeight)
                .background(Color.Green, shape = RoundedCornerShape(10.dp))
                .align(Alignment.BottomCenter)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        TelegramLoading(size = 100.dp)
    }
}
