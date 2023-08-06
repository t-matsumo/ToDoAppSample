package com.example.domain.todo.delete

import com.example.domain.todo.*

class DeleteToDoInteractor(
    private val toDoRepository: ToDoRepository
): DeleteToDoUseCase {
    override fun handle(request: DeleteToDoRequest) {
        val operator = Operator(OperatorId(request.memberId))
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            ?.takeIf { operator.canEdit(it) }
            .let { it ?: throw NoSuchElementException() }
            .let { toDoRepository.delete(it) }
    }
}