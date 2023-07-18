package com.example.domain.todo

import java.time.LocalDateTime

@JvmInline
value class ToDoId(val value: String)

@JvmInline
value class ToDoContent(val value: String)

@JvmInline
value class ToDoCreatedAt(val value: LocalDateTime): Comparable<ToDoCreatedAt> {
    companion object {
        fun now() = ToDoCreatedAt(LocalDateTime.now())
    }

    override fun compareTo(other: ToDoCreatedAt) = this.value.compareTo(other.value)
}

class Todo(
    val id: ToDoId,
    private val content: ToDoContent,
    val createdAt: ToDoCreatedAt
) {
    val idString = id.value
    val contentString = content.value
    val createdAtLocalDateTime = createdAt.value

    fun updatedWith(content: ToDoContent): Todo {
        return Todo(id, content, createdAt)
    }
}
