package com.example.domain.todo.delete

data class DeleteToDoRequest(val id: String)

interface DeleteToDoUseCase {
    fun handle(request: DeleteToDoRequest)
}