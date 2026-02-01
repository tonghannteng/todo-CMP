package com.tonghannteng.todo.domain

import kotlin.time.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class TodoTask(
    val id: String = Uuid.random().toHexString(),
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val priority: Priority,
    val createdAt: Long = Clock.System.now().toEpochMilliseconds(),
    val updateAt: Long = Clock.System.now().toEpochMilliseconds()
)

enum class Priority {
    NONE,
    LOW,
    MEDIUM,
    HIGH
}
