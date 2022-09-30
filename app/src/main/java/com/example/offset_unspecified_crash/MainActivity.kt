package com.example.offset_unspecified_crash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.unit.dp
import com.example.offset_unspecified_crash.ui.theme.OffsetunspecifiedcrashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OffsetunspecifiedcrashTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    var isVisible by remember { mutableStateOf(true) }
                    Button(onClick = { isVisible = !isVisible }) {
                        Text(text = "Click me!")
                    }

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(tween(1000)),
                        exit = fadeOut(tween(1000)),
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        CompositionLocalProvider(LocalViewConfiguration provides NoTouchSlopViewConfiguration()) {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red)
                                    .fillMaxWidth()
                                    .height(75.dp)
                                    .pointerInput(Unit) {
                                        detectDragGestures(
                                            onDrag = { _, _ -> },
                                            onDragEnd = {},
                                            onDragCancel = {}
                                        )
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

class NoTouchSlopViewConfiguration : ViewConfiguration {
    // Can't reproduce when touchSlop is 1f
    override val touchSlop: Float = 0f

    override val longPressTimeoutMillis: Long = android.view.ViewConfiguration.getLongPressTimeout().toLong()
    override val doubleTapTimeoutMillis: Long = android.view.ViewConfiguration.getDoubleTapTimeout().toLong()
    override val doubleTapMinTimeMillis: Long = 40
}