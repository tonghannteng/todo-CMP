package com.tonghannteng.todo.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.koin.compose.koinInject

@Composable
actual fun NavGraph() {
    val navigator = koinInject<Navigator>()

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        entryProvider = entryProvider {
            entry<Screen.Home> {
                Text("Hello world!")
            }
            entry<Screen.Task> {  }
        }
    )

}