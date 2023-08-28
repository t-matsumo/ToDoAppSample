package com.example.application.usecase.task.update

import com.example.application.repository.ToDoRepository
import com.example.domain.todo.*

class UpdateToDoInteractor(
    private val toDoRepository: ToDoRepository
): UpdateToDoUseCase {
    override fun handle(request: UpdateToDoRequest) {
        val operator = Operator(OperatorId(request.operatorId))
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            ?.takeIf { operator.canEdit(it) }
            .let { it ?: throw NoSuchElementException() }
            .let {
                val updatedTodo = it.updatedWith(
                    ToDoTitle(request.title),
                    ToDoContent(request.content)
                )
                toDoRepository.save(updatedTodo)
            }
    }
}