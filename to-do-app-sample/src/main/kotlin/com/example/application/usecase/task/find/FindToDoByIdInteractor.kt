package com.example.application.usecase.task.find

import com.example.domain.todo.Operator
import com.example.domain.todo.OperatorId
import com.example.application.repository.ToDoRepository
import com.example.application.usecase.task.find.response.ToDo

class FindToDoByIdInteractor(private val repository: ToDoRepository): FindToDoByIdUseCase {
    override fun handle(request: FindToDoByIdRequest): FindToDoByIdResponse {
        val operator = Operator(OperatorId(request.operatorId))
        return repository
            .find(repository.idFromString(request.id))
            ?.takeIf { operator.canRead(it) }
            ?.let {
                ToDo(
                    it.idString,
                    it.titleString,
                    it.contentString,
                    it.createdAtLocalDateTime
                )
            }.let{ FindToDoByIdResponse(it) }
    }
}