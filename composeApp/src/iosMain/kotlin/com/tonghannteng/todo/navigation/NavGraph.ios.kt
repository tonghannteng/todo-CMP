package com.tonghannteng.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tonghannteng.todo.presentation.screen.home.HomeScreen
import com.tonghannteng.todo.presentation.screen.task.TaskScreen

@Composable
actual fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(
                navigateToTask = { taskId ->
                    navController.navigate(route = Screen.Task(taskId))
                }
            )
        }
        composable<Screen.Task> {
            TaskScreen(
                id = it.id,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}