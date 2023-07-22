package com.example.domain.todo.update

import com.example.domain.todo.ToDoContent
import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.ToDoTitle

class UpdateToDoInteractor(
    private val toDoRepository: ToDoRepository
): UpdateToDoUseCase {
    override fun handle(request: UpdateToDoRequest) {
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            .onSuccess {
                val updatedTodo = it.updatedWith(
                    ToDoTitle(request.title),
                    ToDoContent(request.content)
                )
                toDoRepository.save(updatedTodo)
            }
    }
}