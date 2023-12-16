package ui

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import ui.Screen.*
import ui.pages.About
import ui.pages.AddCountdown
import ui.pages.Home
import ui.pages.Settings

enum class Screen {
    Home, Settings, About, AddCountdown
}

val navStack = ArrayDeque<Screen>()

@Composable
fun NavHost(yosNav: MutableState<Screen>) {
    LaunchedEffect(Unit) {
        navStack.addFirst(Home)
    }
    AnimatedContent(targetState = yosNav.value,
        transitionSpec = {
            slideInHorizontally {
                it
            } togetherWith scaleOut(targetScale = 0.95f) + fadeOut()
        }) { screen ->
        when (screen) {
            Home -> Home()
            Settings -> Settings()
            About -> About()
            AddCountdown -> AddCountdown()
        }
    }
}

fun goScreen(yosNav: MutableState<Screen>, screen: Screen) {
    if (yosNav.value == screen) {
        return
    }
    navStack.addFirst(screen)
    yosNav.value = screen
}

fun popBack(yosNav: MutableState<Screen>) {
    if (navStack.size <= 1) {
        yosNav.value = Home
    } else {
        navStack.removeFirst()
        yosNav.value = navStack.first()
    }
}
