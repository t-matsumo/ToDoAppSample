package com.example.application.usecase.task.delete

data class DeleteToDoRequest(val memberId: String, val id: String)

interface DeleteToDoUseCase {
    fun handle(request: DeleteToDoRequest)
}