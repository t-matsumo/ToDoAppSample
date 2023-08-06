package com.example.domain.todo.delete

import com.example.domain.todo.AuthorId
import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.Todo

class DeleteToDoInteractor(
    private val toDoRepository: ToDoRepository
): DeleteToDoUseCase {
    override fun handle(request: DeleteToDoRequest) {
        toDoRepository
            .find(toDoRepository.idFromString(request.id))
            .onSuccess {
                if (it.canBeDeleted(AuthorId(request.memberId))) {
                    toDoRepository.delete(it)
                }
            }
    }
}

data class OperatorPrivilege(val id: AuthorId) {
    fun canDelete(toDo: Todo): Boolean {
        return toDo.authorId == id
    }
}