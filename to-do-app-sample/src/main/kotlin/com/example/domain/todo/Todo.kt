package com.example.domain.todo

import java.time.LocalDateTime

@JvmInline
value class ToDoId(val value: String) {
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

@JvmInline
value class AuthorId(val value: String)

class Todo(
    val id: ToDoId,
    val authorId: AuthorId,
    val title: ToDoTitle,
    val content: ToDoContent,
    val createdAt: ToDoCreatedAt
) {
    val idString = id.value
    val titleString = title.value
    val contentString = content.value
    val createdAtLocalDateTime = createdAt.value

    fun updatedWith(title: ToDoTitle, content: ToDoContent): Todo {
        return Todo(id, authorId, title, content, createdAt)
    }

    fun canBeDeleted(authorId: AuthorId): Boolean {
        return this.authorId == authorId
    }
}
