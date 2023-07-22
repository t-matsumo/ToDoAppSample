package com.example.domain.todo.find

import com.example.domain.todo.find.response.ToDo

data class FindRecentToDoRequest(val maxCount: Int)

data class FindRecentToDoResponse(val toDos: List<ToDo>)

interface FindRecentToDoUseCase {
    fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse
}