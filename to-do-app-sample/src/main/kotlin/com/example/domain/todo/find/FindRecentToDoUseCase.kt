package com.example.domain.todo.find

import java.time.LocalDateTime
import java.util.*

data class FindRecentToDoRequest(val maxCount: Int)

data class ToDo(
    private val id: UUID,
    val content: String,
    val createdAt: LocalDateTime
)

data class FindRecentToDoResponse(val toDos: List<ToDo>)

interface FindRecentToDoUseCase {
    fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse
}