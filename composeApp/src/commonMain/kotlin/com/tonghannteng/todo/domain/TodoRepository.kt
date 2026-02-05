package com.tonghannteng.todo.domain

import com.tonghannteng.todo.util.RequestState
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun createTask(task: TodoTask): RequestState<Unit>
    fun updateTask(task: TodoTask): RequestState<Unit>
    fun readSelectedTask(taskId: String): RequestState<TodoTask>
    fun readAllTasks(): Flow<RequestState<List<TodoTask>>>
    fun removeTask(taskId: String): RequestState<Unit>
}
