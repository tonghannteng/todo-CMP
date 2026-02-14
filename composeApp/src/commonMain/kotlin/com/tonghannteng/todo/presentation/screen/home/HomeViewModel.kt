package com.tonghannteng.todo.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonghannteng.todo.domain.TodoRepository
import com.tonghannteng.todo.domain.TodoTask
import com.tonghannteng.todo.util.RequestState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val allTasks = repository.readAllTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RequestState.Loading
        )

//    val allTasks: StateFlow<RequestState<List<TodoTask>>> = flowOf(RequestState.Error(message = "Reading error"))
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = RequestState.Loading
//        )
//
//    val allTasks: StateFlow<RequestState<List<TodoTask>>> = flowOf(RequestState.Loading)
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = RequestState.Loading
//        )

    fun markTaskAsCompleted(task: TodoTask): RequestState<Unit> {
        return repository.updateTask(task)
    }

    fun removeTask(taskId: String): RequestState<Unit> {
        return repository.removeTask(taskId = taskId)
    }
}
