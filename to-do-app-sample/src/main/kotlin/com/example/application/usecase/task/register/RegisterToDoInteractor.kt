package com.example.application.usecase.task.register

import com.example.application.repository.ToDoRepository
import com.example.domain.todo.*

class RegisterToDoInteractor(
    private val toDoRepository: ToDoRepository
): RegisterToDoUseCase {
    override fun handle(request: RegisterToDoRequest) {
        val toDo = Todo(
            toDoRepository.nextId(),
            OperatorId(request.memberId),
            ToDoTitle(request.title),
            ToDoContent(request.content),
            ToDoCreatedAt.now()
        )

        toDoRepository.save(toDo)
    }
}