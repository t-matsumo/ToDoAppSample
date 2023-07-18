package com.example.domain.todo.find

import java.time.LocalDateTime

data class FindRecentToDoRequest(val maxCount: Int)

data class ToDo(
    private val id: String,
    val content: String,
    val createdAt: LocalDateTime
)

data class FindRecentToDoResponse(val toDos: List<ToDo>)

interface FindRecentToDoUseCase {
    fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse
}