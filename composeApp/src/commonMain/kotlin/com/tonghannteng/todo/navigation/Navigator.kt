package com.tonghannteng.todo.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class Navigator {
    val backStack: SnapshotStateList<Screen> = mutableStateListOf(Screen.Home)

    fun navigateTo(screen: Screen) {
        backStack.add(screen)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
