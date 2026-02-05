package com.tonghannteng.todo.data

import androidx.compose.runtime.mutableStateListOf
import com.tonghannteng.todo.domain.Priority
import com.tonghannteng.todo.domain.TodoRepository
import com.tonghannteng.todo.domain.TodoTask
import com.tonghannteng.todo.util.RequestState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeTodoRepository: TodoRepository {
    private val tasks = mutableStateListOf<TodoTask>()

    init {
        tasks.addAll(
            listOf(
                TodoTask(
                    title = "Simple Task 1",
                    description = "Some random text.",
                    isCompleted = true,
                    priority = Priority.LOW
                ),
                TodoTask(
                    title = "Simple Task 2",
                    description = "Some random text.",
                    isCompleted = false,
                    priority = Priority.MEDIUM
                )
            )
        )
    }

    override fun createTask(task: TodoTask): RequestState<Unit> {
        return try {
            val existingTask = tasks.find { it.id == task.id }
            if (existingTask == null) {
                tasks.add(task)
                RequestState.Success(data = Unit)
            } else {
                RequestState.Error(message = "Task with ID: ${task.id} already exists.")
            }

        } catch (e: Exception) {
            RequestState.Error(message = "Failed to create a task: ${e.message}")
        }
    }

    override fun updateTask(task: TodoTask): RequestState<Unit> {
        return try {
            val index = tasks.indexOfFirst { it.id == task.id }
            if (index != -1) {
                tasks[index] = task
                RequestState.Success(data = Unit)
            } else {
                RequestState.Error(message = "Task with ID: ${task.id} not found.")
            }
        } catch (e: Exception) {
            RequestState.Error(message = "Failed to update a task: ${e.message}")
        }
    }

    override fun readSelectedTask(taskId: String): RequestState<TodoTask> {
        return try {
            val existingTask = tasks.find { it.id == taskId }
            if (existingTask != null) {
                RequestState.Success(data = existingTask)
            } else {
                RequestState.Error(message = "Task with ID: $taskId not found.")
            }
        } catch (e: Exception) {
            RequestState.Error(message = "Failed to read a selected task: ${e.message}")
        }
    }

    override fun readAllTasks(): Flow<RequestState<List<TodoTask>>> {
        return try {
            flowOf(RequestState.Success(data = tasks))
        } catch (e: Exception) {
            flowOf(RequestState.Error(message = "Failed to read all tasks ${e.message}"))
        }
    }

    override fun removeTask(taskId: String): RequestState<Unit> {
        return try {
            val taskToRemove = tasks.find { it.id == taskId }
            if (taskToRemove != null) {
                tasks.remove(element = taskToRemove)
                RequestState.Success(data = Unit)
            } else {
                RequestState.Error(message = "Task with ID: $taskId not found.")
            }
        } catch (e: Exception) {
            RequestState.Error(message = "Failed to remove the task: ${e.message}")
        }
    }
}
