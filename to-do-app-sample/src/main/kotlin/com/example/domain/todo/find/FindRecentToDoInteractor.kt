package com.example.domain.todo.find

import com.example.domain.todo.ToDoRepository

class FindRecentToDoInteractor(
    private val toDoRepository: ToDoRepository
): FindRecentToDoUseCase {
    override fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse {
        val toDos = toDoRepository
            .findRecent(request.maxCount)
            .map {
                ToDo(
                    it.idUUID,
                    it.contentString,
                    it.createdAtLocalDateTime
                )
            }
        return FindRecentToDoResponse(toDos)
    }
}