package com.tonghannteng.todo.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.domain.TodoRepository
import com.tonghannteng.todo.domain.TodoTask
import com.tonghannteng.todo.util.RequestState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private var _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private var _priorityFilter = MutableStateFlow(Priority.NONE)
    val priorityFilter = _priorityFilter.asStateFlow()

    val allTasks = combine(
        flow = repository.readAllTasks(),
        flow2 = _priorityFilter,
        flow3 = _searchQuery
    ) { tasks, priority, query ->
        when (tasks) {
            is RequestState.Success -> {
                val filterTasks = tasks.data
                    .let { list ->
                        if (priority == Priority.NONE) list
                        else list.filter { it.priority == priority }
                    }
                    .let { list ->
                        if (query.isBlank()) list
                        else list.filter {
                            it.title.lowercase().contains(query, ignoreCase = false) ||
                                    it.description.lowercase().contains(query, ignoreCase = false)
                        }
                    }.sortedByDescending { it.priority.ordinal }
                RequestState.Success(data = filterTasks)
            }
            else -> tasks
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RequestState.Loading
    )


//    val allTasks = repository.readAllTasks()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = RequestState.Loading
//        )

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

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun updatePriorityFilter(priority: Priority) {
        _priorityFilter.value = priority
    }
}
