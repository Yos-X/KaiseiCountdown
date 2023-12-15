package ui.pages

import androidx.compose.animation.core.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(loadCompleted: MutableState<Boolean>,onLoading:CoroutineScope.() -> Unit) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var loadingStatus by rememberSaveable {
                mutableStateOf(0)
                // 0: 大
                // 1: 中、旋转
                // 2: 大、旋转、透明
            }

            var scaleStatus by rememberSaveable {
                mutableStateOf(0)
                // 0: 大
                // 1: 中
            }

            var rotateStatus by rememberSaveable {
                mutableStateOf(0)
                // 0: 不转
                // 1: 转
            }

            var alphaStatus by rememberSaveable {
                mutableStateOf(0)
                // 0: 不透明
                // 1: 透明
            }

            val scale by animateFloatAsState(if (scaleStatus == 0) 2.5f else 1f, animationSpec = tween(140))
            val rotate by animateFloatAsState(
                if (rotateStatus == 0) 0f else 360f, animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 25000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            val alpha by animateFloatAsState(if (alphaStatus == 0) 1f else 0f, animationSpec = tween(250))
            LaunchedEffect(null) {
                scaleStatus = 1
                delay(250)
                rotateStatus = 1

                onLoading()
                delay(1000)

                scaleStatus = 0
                alphaStatus = 1

                delay(250)
                loadCompleted.value = true
            }
            Image(
                painter = painterResource("sakura.png"),
                contentDescription = "sakura",
                modifier = Modifier.size(120.dp).scale(scale).rotate(rotate).alpha(alpha)
            )
        }
    }
}