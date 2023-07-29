package com.example.domain.todo.find

import com.example.domain.todo.AuthorId
import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.Todo
import com.example.domain.todo.find.response.ToDo

class FindRecentToDoInteractor(
    private val findRecentToDoQuery: FindRecentToDoQuery
): FindRecentToDoUseCase {
    private val toDoFinder = ToDoFinder(findRecentToDoQuery)

    override fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse {
        return toDoFinder.findRecentToDo(AuthorId(request.memberId), request.maxCount)
            .map {
                ToDo(
                    it.idString,
                    it.titleString,
                    it.contentString,
                    it.createdAtLocalDateTime
                )
            }.let {
                FindRecentToDoResponse(it)
            }
    }
}