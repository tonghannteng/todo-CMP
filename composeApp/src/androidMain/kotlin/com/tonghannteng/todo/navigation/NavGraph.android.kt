package com.tonghannteng.todo.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.tonghannteng.todo.presentation.component.InfoCard
import com.tonghannteng.todo.presentation.screen.home.HomeScreen
import com.tonghannteng.todo.presentation.screen.task.TaskScreen
import com.tonghannteng.todo.util.Resource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
actual fun NavGraph() {
    val navigator = koinInject<Navigator>()

    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val directive = remember(key1 = windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo).copy(horizontalPartitionSpacerSize = 0.dp)
    }

    val listDetailStrategy = rememberListDetailSceneStrategy<Any>(directive = directive)

    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        sceneStrategy = listDetailStrategy,
        entryProvider = entryProvider {
            entry<Screen.Home>(
                metadata = ListDetailSceneStrategy.listPane(
                    detailPlaceholder = {
                        InfoCard(
                            lightModeIcon = Resource.Image.PAINTING_LIGHT,
                            darkModeIcon = Resource.Image.PAINTING_DARK,
                            message = "Select an existing Task or create a new one.",
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                )
            ) {
                HomeScreen(navigateToTask = { taskId ->
                    navigator.navigateTo(Screen.Task(id = taskId))
                })
            }
            entry<Screen.Task>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) {
                TaskScreen(
                    id = it.id,
                    navigateBack = {
                        navigator.goBack()
                    }
                )
            }
        }
    )
}
