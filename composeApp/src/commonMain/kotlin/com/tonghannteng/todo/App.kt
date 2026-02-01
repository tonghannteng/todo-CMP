package com.tonghannteng.todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.tonghannteng.todo.navigation.NavGraph
import com.tonghannteng.todo.util.darkScheme
import com.tonghannteng.todo.util.lightScheme

@Composable
@Preview
fun App() {
    val colorScheme = if (isSystemInDarkTheme()) darkScheme else lightScheme

    MaterialTheme(colorScheme) {
        NavGraph()
    }
}
