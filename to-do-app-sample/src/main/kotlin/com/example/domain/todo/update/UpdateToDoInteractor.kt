package com.example.domain.todo.update

import com.example.domain.todo.ToDoContent
import com.example.domain.todo.ToDoRepository

class UpdateToDoInteractor(
    private val toDoRepository: ToDoRepository
): UpdateToDoUseCase {
    override fun handle(request: UpdateToDoRequest) {
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            .onSuccess {
                val updatedTodo = it.updatedWith(ToDoContent(request.content))
                toDoRepository.save(updatedTodo)
            }
    }
}