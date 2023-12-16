@file:OptIn(DelicateCoroutinesApi::class)

package ui.x

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.Minimize
import androidx.compose.material.icons.filled.OpenInFull
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.YosRoundedCornerShape

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ApplicationScope.YosBasicWindow(title:String, size: DpSize = DpSize(Dp.Unspecified,Dp.Unspecified), maxSize:Pair<Int,Int>? = null, content: @Composable (FrameWindowScope.() -> Unit)){
    var maximize by rememberSaveable {
        mutableStateOf(false)
    }
    val state = rememberWindowState(
        position = WindowPosition(alignment = Alignment.Center),
        size = size
    )
    var lastPositionX by rememberSaveable {
        mutableStateOf(0)
    }
    var lastPositionY by rememberSaveable {
        mutableStateOf(0)
    }
    var lastSize by rememberSaveable {
        mutableStateOf(Pair(0,0))
    }
    var fullSize by rememberSaveable {
        mutableStateOf(Pair(0,0))
    }

    if (fullSize.first != 0 && fullSize.second != 0){
        state.size = DpSize(
            animateDpAsState(if (maximize) fullSize.first.dp else lastSize.first.dp,
            tween(200)
        ).value, animateDpAsState(if (maximize) fullSize.second.dp else lastSize.second.dp,
            tween(200)
        ).value)
    }

    /*val xRaw by derivedStateOf {
        if (maximize) 0.dp else lastPositionX.dp
    }

    val yRaw by derivedStateOf {
        if (maximize) 0.dp else lastPositionY.dp
    }

    val x = animateDpAsState(
        xRaw
    )
    val y = animateDpAsState(
        yRaw
    )
    state.position = WindowPosition(x.value,y.value)*/

    var scaleStatus by rememberSaveable {
        mutableStateOf(0)
    }

    val exitApp = fun (){
        GlobalScope.launch {
            scaleStatus = 0
            delay(200)
            //::exitApplication.invoke()
            exitApplication()
        }
    }

    Window(
        title = title,
        onCloseRequest = exitApp,
        undecorated = true,
        state = state,
        transparent = true,
        resizable = /*!maximize*/ false
    ) {
        KaiseiTheme {
            val scale by animateFloatAsState(if (scaleStatus == 0) 0.8f else 1f, animationSpec = tween(200))
            val alpha by animateFloatAsState(if (scaleStatus == 0) 0.1f else 1f, animationSpec = tween(150))

            LaunchedEffect(Unit) {
                scaleStatus = 1
            }

            val padding by animateDpAsState(if (!maximize) 6.dp else 0.dp, tween(200))
            Surface(
                modifier = (if (padding > 0.dp) Modifier.padding(padding) else Modifier).fillMaxSize()
                    .scale(scale).alpha(alpha),
                shape = YosRoundedCornerShape(animateDpAsState(if (!maximize) 15.dp else 0.dp, tween(200)).value),
                shadowElevation = 2.dp,
                tonalElevation = 1.dp
            ) {
                content()
                Row(modifier = Modifier.fillMaxWidth().padding(start = 80.dp)) {
                    MutableDraggable(!maximize) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(30.dp).height(40.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            var miniActive by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.size(35.dp)
                                .clip(YosRoundedCornerShape(10.dp))
                                .onPointerEvent(PointerEventType.Enter) { miniActive = true }
                                .onPointerEvent(PointerEventType.Exit) { miniActive = false }
                                .clickable {
                                    GlobalScope.launch {
                                        scaleStatus = 0
                                        delay(200)
                                        state.isMinimized = true
                                        scaleStatus = 1
                                    }
                                },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Minimize,
                                    "Minimize",
                                    modifier = Modifier.size(20.dp).alpha(0.8f),
                                    tint = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                            //var maxActive by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.size(35.dp)
                                .clip(YosRoundedCornerShape(10.dp))
                                /*.onPointerEvent(PointerEventType.Enter) { maxActive = true }
                                .onPointerEvent(PointerEventType.Exit) { maxActive = false }*/
                                .clickable(enabled = false) {
                                    GlobalScope.launch {
                                        if (!maximize) {
                                            lastPositionX = state.position.x.value.toInt()
                                            lastPositionY = state.position.y.value.toInt()
                                            lastSize =
                                                Pair(state.size.width.value.toInt(), state.size.height.value.toInt())
                                        }

                                        maximize = !maximize
                                    }
                                }
                                .alpha(0.2f),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    if (!maximize) Icons.Default.OpenInFull else Icons.Default.CloseFullscreen,
                                    "FullScreenSwitch",
                                    modifier = Modifier.size(20.dp).alpha(0.8f),
                                    tint = Color.Black
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                            var closeActive by remember { mutableStateOf(false) }
                            Box(modifier = Modifier.size(35.dp)
                                .clip(YosRoundedCornerShape(10.dp))
                                .onPointerEvent(PointerEventType.Enter) { closeActive = true }
                                .onPointerEvent(PointerEventType.Exit) { closeActive = false }
                                .clickable {
                                    exitApp()
                                }
                                .background(
                                    animateColorAsState(
                                        if (closeActive) MaterialTheme.colorScheme.error else Color.Transparent,
                                        tween(150)
                                    ).value
                                ),
                                contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.Default.Close,
                                    "Close",
                                    modifier = Modifier.size(20.dp).alpha(0.8f),
                                    tint = animateColorAsState(
                                        if (closeActive) Color.White else Color.Black,
                                        tween(150)
                                    ).value
                                )
                            }
                        }
                    }
                }
            }
        }
        LaunchedEffect(Unit) {
            delay(200)
            if (fullSize.first == 0 && fullSize.second == 0 && window.width != 0 && window.height != 0 && maxSize == null) {
                fullSize = Pair(window.width, window.height)
                lastSize = Pair(window.width, window.height)
            }else if(maxSize != null){
                fullSize = maxSize
                lastSize = Pair(window.width, window.height)
            }
        }
    }
}

@Composable
fun WindowScope.MutableDraggable(canDrag: Boolean, content: @Composable () -> Unit) {
    return if (canDrag) {
        WindowDraggableArea {
            content()
        }
    } else{
        content()
    }
}