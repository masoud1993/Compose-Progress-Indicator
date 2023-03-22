package com.masa.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.masa.composetest.ui.theme.ComposeTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    TelegramLoading()
                }
            }
        }
    }
}

@Composable
fun TelegramLoading() {

    var isClicked by remember {
        mutableStateOf(false)
    }
    val height by animateDpAsState(if (isClicked) 0.dp else 100.dp, animationSpec = tween(500, delayMillis = 200))
    val crossHeight by animateDpAsState(if (isClicked) 0.dp else 40.dp, animationSpec = tween(500, delayMillis = 200))
    val closeHeight by animateDpAsState(if (!isClicked) 0.dp else 40.dp, animationSpec = tween(500, delayMillis = 200))

    Box(
        modifier = Modifier
            .clickable {
                isClicked = !isClicked
            }
            .size(100.dp)
            .clipToBounds()
            .clip(CircleShape)
            .background(Color.Blue)
            .padding(4.dp)
            .border(
                width = 9.dp,
                color = Color.White,
                shape = CircleShape
            )
    ){

        Box(modifier = Modifier.align(Alignment.Center)){
            Box(modifier = Modifier
                .rotate(-45F)
                .size(9.dp, closeHeight)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
            )

            Box(modifier = Modifier
                .rotate(45F)
                .size(9.dp, closeHeight)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
            )
        }

        Box(modifier = Modifier
            .size(9.dp, height)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .align(Alignment.BottomCenter)
        )
        Box(modifier = Modifier
            .padding(end = 20.dp)
            .rotate(-45F)
            .size(9.dp, crossHeight)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .align(Alignment.BottomCenter)
        )

        Box(modifier = Modifier
            .padding(start = 20.dp)
            .rotate(45F)
            .size(9.dp, crossHeight)
            .background(Color.White, shape = RoundedCornerShape(10.dp))
            .align(Alignment.BottomCenter)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestTheme {
        TelegramLoading()
    }
}