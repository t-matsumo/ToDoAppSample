package com.example.domain.todo.find

import com.example.domain.todo.ToDoRepository

class FindRecentToDoInteractor(
    private val toDoRepository: ToDoRepository
): FindRecentToDoUseCase {
    override fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse {
        return toDoRepository
            .findRecent(request.maxCount)
            .map {
                ToDo(
                    it.idString,
                    it.contentString,
                    it.createdAtLocalDateTime
                )
            }.let {
                FindRecentToDoResponse(it)
            }
    }
}