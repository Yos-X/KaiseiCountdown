import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.*
import ui.pages.SplashScreen
import ui.x.YosBasicWindow
import utils.Kaisei


@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, Desktop!"
        }) {
            Text(text)
        }
    }
}

val errorDialog = mutableStateOf(false)
val errorMessage = mutableStateOf("错误信息")
val yosNav = mutableStateOf(Screen.Home)

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    val loadCompleted = rememberSaveable {
        mutableStateOf(false)
    }

    /*var fullSize by rememberSaveable {
        mutableStateOf(Pair(0,0))
    }*/

    if (!loadCompleted.value) {
        val splashState =
            rememberWindowState(position = WindowPosition(Alignment.Center), size = DpSize(400.dp,400.dp))
        Window(
            state = splashState,
            onCloseRequest = {},
            undecorated = true,
            transparent = true,
            enabled = false,
            focusable = false,
            alwaysOnTop = true
        ) {
            SplashScreen(loadCompleted){
                //fullSize = Pair(window.width, window.height)
            }
        }
    } else {
        YosBasicWindow(title = "快晴", size = DpSize(900.dp, 640.dp)) {
            if (errorDialog.value) {
                LaunchedEffect(Unit) {
                    Kaisei().resetCountdown()
                }
                AlertDialog(
                    onDismissRequest = {
                        errorDialog.value = false
                    },
                    icon = {
                        Icon(Icons.Default.Warning, "Warning")
                    },
                    title = {
                        Text(text = "运行时错误")
                    },
                    text = {
                        Text(text = "快晴在运行时发生了错误，倒计时配置已被重置。\n以下是错误日志：\n" + errorMessage.value)
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                errorDialog.value = false
                            }
                        ) {
                            Text("好")
                        }
                    }
                )
            }

            Row {
                var selectedItem by remember { mutableIntStateOf(0) }
                selectedItem = when (yosNav.value) {
                    Screen.Home -> 0
                    Screen.Settings -> 1
                    Screen.About -> 2
                    else -> -1
                }
                val showBack = navStack.size > 1
                val items = listOf("主页", "设置", "关于")
                val icons = listOf(Icons.Filled.Home, Icons.Filled.Settings, Icons.Filled.Info)
                Surface(modifier = Modifier.fillMaxHeight(), tonalElevation = 2.dp) {
                    NavigationRail(modifier = Modifier.fillMaxHeight().padding(top = 30.dp),
                        header = {
                            var backActive by remember { mutableStateOf(false) }
                            Column(modifier = Modifier.size(35.dp)) {
                                AnimatedVisibility(
                                    visible = showBack,
                                    enter = scaleIn() + fadeIn(),
                                    exit = scaleOut() + fadeOut()
                                ) {
                                    Box(modifier = Modifier.size(35.dp)
                                        .clip(YosRoundedCornerShape(10.dp))
                                        .onPointerEvent(PointerEventType.Enter) { backActive = true }
                                        .onPointerEvent(PointerEventType.Exit) { backActive = false }
                                        .clickable {
                                            popBack(yosNav)
                                        },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            Icons.Default.ArrowBack,
                                            "ArrowBack",
                                            modifier = Modifier.size(20.dp).alpha(0.8f),
                                            tint = Color.Black
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Image(
                                painter = painterResource("sakura.png"),
                                contentDescription = "sakura",
                                modifier = Modifier.size(50.dp)
                            )
                        }) {
                        Spacer(modifier = Modifier.height(10.dp))
                        items.forEachIndexed { index, item ->
                            NavigationRailItem(
                                icon = { Icon(icons[index], contentDescription = item) },
                                label = { Text(item) },
                                selected = selectedItem == index,
                                onClick = {
                                    selectedItem = index
                                    goScreen(
                                        yosNav, when (index) {
                                            0 -> Screen.Home
                                            1 -> Screen.Settings
                                            2 -> Screen.About
                                            else -> Screen.Home
                                        }
                                    )
                                },
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    }
                }

                NavHost(yosNav)
            }
        }
    }

}
