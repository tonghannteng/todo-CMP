package com.tonghannteng.todo.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tonghannteng.todo.presentation.screen.home.HomeScreen
import org.koin.compose.koinInject

@Composable
actual fun NavGraph() {
    val navigator = koinInject<Navigator>()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(navigateToTask = { taskId ->
                    navigator.navigateTo(Screen.Task(id = taskId))
                })
            }
            entry<Screen.Task> {
                Text(text = "Task Screen")
            }
        }
    )
}
