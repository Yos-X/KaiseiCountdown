import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.NavHost
import ui.Screen
import ui.pages.SplashScreen
import ui.x.YosBasicWindow


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
        YosBasicWindow(title = "Kaisei", size = DpSize(Dp.Unspecified,Dp.Unspecified)) {
            val nowPage = remember {
                mutableStateOf(Screen.Home)
            }

            NavHost(nowPage)
        }
    }

}
