package com.tonghannteng.todo

import androidx.compose.ui.window.ComposeUIViewController
import com.tonghannteng.todo.di.initializeKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initializeKoin() }
) { App() }