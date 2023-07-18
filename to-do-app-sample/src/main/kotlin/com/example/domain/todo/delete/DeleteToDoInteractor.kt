package com.example.domain.todo.delete

import com.example.domain.todo.ToDoRepository

class DeleteToDoInteractor(
    private val toDoRepository: ToDoRepository
): DeleteToDoUseCase {
    override fun handle(request: DeleteToDoRequest) {
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            .onSuccess {
                toDoRepository.delete(it)
            }
    }
}