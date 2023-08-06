package com.example.domain.todo.find

import com.example.domain.todo.*
import com.example.domain.todo.find.response.ToDo

class FindRecentToDoInteractor(
    private val findRecentToDoQuery: FindRecentToDoQuery
): FindRecentToDoUseCase {
    private val toDoFinder = ToDoFinder(findRecentToDoQuery)

    override fun handle(request: FindRecentToDoRequest): FindRecentToDoResponse {
        val operator = Operator(OperatorId(request.memberId))
        return toDoFinder.findRecentToDoBy(operator, request.maxCount)
            .map {
                ToDo(
                    it.idString,
                    it.titleString,
                    it.contentString,
                    it.createdAtLocalDateTime
                )
            }
            .let { FindRecentToDoResponse(it) }
    }
}