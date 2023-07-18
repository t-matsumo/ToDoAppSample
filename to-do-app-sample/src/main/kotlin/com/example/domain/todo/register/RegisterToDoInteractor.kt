package com.example.domain.todo.register

import com.example.domain.todo.ToDoContent
import com.example.domain.todo.ToDoCreatedAt
import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.Todo

class RegisterToDoInteractor(
    private val toDoRepository: ToDoRepository
): RegisterToDoUseCase {
    override fun handle(request: RegisterToDoRequest) {
        val toDo = Todo(
            toDoRepository.nextId(),
            ToDoContent(request.content),
            ToDoCreatedAt.now()
        )

        toDoRepository.save(toDo)
    }
}