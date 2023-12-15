package ui

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.*
import ui.Screen.*
import ui.pages.Home

@Composable
fun NavHost(currentPage:MutableState<Screen>){
    Crossfade(targetState = currentPage.value) { screen ->
        when (screen) {
            Home -> Home()
            Settings -> TODO()
            About -> TODO()
        }
    }
}

enum class Screen {
    Home, Settings, About
}
