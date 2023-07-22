package com.example.domain.todo

import java.time.LocalDateTime

@JvmInline
value class ToDoId(val value: String)

@JvmInline
value class ToDoTitle(val value: String)

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
    private val title: ToDoTitle,
    private val content: ToDoContent,
    val createdAt: ToDoCreatedAt
) {
    val idString = id.value
    val titleString = title.value
    val contentString = content.value
    val createdAtLocalDateTime = createdAt.value


    fun updatedWith(title: ToDoTitle, content: ToDoContent): Todo {
        return Todo(id, title, content, createdAt)
    }
}
