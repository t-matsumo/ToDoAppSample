package com.example.application.usecase.task.find

import com.example.application.usecase.task.find.response.ToDo

data class FindRecentToDoRequest(val memberId: String, val maxCount: Int)

data class FindRecentToDoResponse(val toDos: List<ToDo>)

interface FindRecentToDoUseCase {
    fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse
}