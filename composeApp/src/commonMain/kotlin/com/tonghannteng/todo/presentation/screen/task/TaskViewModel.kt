package com.tonghannteng.todo.presentation.screen.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.domain.TodoRepository
import com.tonghannteng.todo.domain.TodoTask
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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

    fun loadData(taskId: String?) {
        if (taskId != null) {
            val existingTask = repository.readSelectedTask(taskId = taskId)
            if (existingTask.isSuccess()) {
                _uiState.value = TaskUiState(
                    id = taskId,
                    title = existingTask.getSuccessData().title,
                    description = existingTask.getSuccessData().description,
                    priority = existingTask.getSuccessData().priority
                )
            }
        } else {
            _uiState.value = TaskUiState()
        }
    }

    fun updateTitle(title: String) {
        _uiState.value = uiState.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _uiState.value = uiState.value.copy(description = description)
    }

    fun updatePriority(priority: Priority) {
        _uiState.value = uiState.value.copy(priority = priority)
    }

    @OptIn(ExperimentalUuidApi::class)
    fun saveTask(
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val uiStateData = _uiState.value

        val task = TodoTask(
            id = uiStateData.id ?: Uuid.random().toHexString(),
            title = uiStateData.title,
            description = uiStateData.description,
            priority = uiStateData.priority
        )

        val result = if (uiStateData.id != null) {
            repository.updateTask(task = task)
        } else {
            repository.createTask(task)
        }

        if (result.isSuccess()) {
            onSuccess()
        } else {
            onError(result.getErrorMessage())
        }
    }
}
