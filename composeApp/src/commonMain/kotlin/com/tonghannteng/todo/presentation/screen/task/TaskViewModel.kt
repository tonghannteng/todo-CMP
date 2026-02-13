package com.tonghannteng.todo.presentation.screen.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.domain.TodoRepository

data class TaskUiState(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.LOW,
    val error: String? = null
)

class TaskViewModel(
    private val repository: TodoRepository
): ViewModel() {

    private var _uiState: MutableState<TaskUiState> = mutableStateOf(TaskUiState())
    val uiState: State<TaskUiState> = _uiState


}