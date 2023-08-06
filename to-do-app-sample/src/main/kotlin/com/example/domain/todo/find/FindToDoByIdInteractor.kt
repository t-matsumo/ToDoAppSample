package com.example.domain.todo.find

import com.example.domain.todo.Operator
import com.example.domain.todo.OperatorId
import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.find.response.ToDo

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