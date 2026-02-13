package com.tonghannteng.todo.di

import com.tonghannteng.todo.data.FakeTodoRepository
import com.tonghannteng.todo.domain.TodoRepository
import com.tonghannteng.todo.navigation.Navigator
import com.tonghannteng.todo.presentation.screen.home.HomeViewModel
import com.tonghannteng.todo.presentation.screen.task.TaskViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val koinModule = module {
    singleOf(constructor = ::Navigator)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TaskViewModel)
    single<TodoRepository> { FakeTodoRepository() }
}

fun initializeKoin(config: (KoinApplication. () -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(koinModule)
    }
}