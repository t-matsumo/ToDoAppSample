package com.example.domain.todo

import java.time.LocalDateTime
import java.util.UUID

@JvmInline
value class ToDoId(val value: UUID)

@JvmInline
value class ToDoContent(val value: String)

@JvmInline
value class ToDoCreatedAt(val value: LocalDateTime): Comparable<ToDoCreatedAt> {
    override fun compareTo(other: ToDoCreatedAt) = this.value.compareTo(other.value)
}

class Todo(
    val id: ToDoId,
    private val content: ToDoContent,
    val createdAt: ToDoCreatedAt
) {
    val idUUID = id.value
    val contentString = content.value
    val createdAtLocalDateTime = createdAt.value
}
