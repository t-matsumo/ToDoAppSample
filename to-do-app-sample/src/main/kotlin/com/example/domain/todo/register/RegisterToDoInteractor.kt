package com.example.domain.todo.register

import com.example.domain.todo.*

class RegisterToDoInteractor(
    private val toDoRepository: ToDoRepository
): RegisterToDoUseCase {
    override fun handle(request: RegisterToDoRequest) {
        val toDo = Todo(
            toDoRepository.nextId(),
            AuthorId(request.memberId),
            ToDoTitle(request.title),
            ToDoContent(request.content),
            ToDoCreatedAt.now()
        )

        toDoRepository.save(toDo)
    }
}