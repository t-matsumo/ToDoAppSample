package com.example.domain.todo

import java.time.LocalDateTime

@JvmInline
value class Id(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ToDoTitle(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

@JvmInline
value class ToDoContent(val value: String)

@JvmInline
value class ToDoCreatedAt(val value: LocalDateTime): Comparable<ToDoCreatedAt> {
    companion object {
        fun now() = ToDoCreatedAt(LocalDateTime.now())
    }

    override fun compareTo(other: ToDoCreatedAt) = this.value.compareTo(other.value)
}

data class Todo(
    val id: Id,
    val operatorId: OperatorId,
    val title: ToDoTitle,
    val content: ToDoContent,
    val createdAt: ToDoCreatedAt
) {
    val idString = id.value
    val titleString = title.value
    val contentString = content.value
    val createdAtLocalDateTime = createdAt.value

    fun updatedWith(toDoTitle: ToDoTitle, toDoContent: ToDoContent): Todo {
        return this.copy(title = toDoTitle, content = toDoContent)
    }
}
