package com.example.domain.todo.delete

data class DeleteToDoRequest(val memberId: String, val id: String)

interface DeleteToDoUseCase {
    fun handle(request: DeleteToDoRequest)
}