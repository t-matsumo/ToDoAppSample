package com.example.domain.todo.update

data class UpdateToDoRequest(val operatorId: String, val id: String, val title: String, val content: String)
interface UpdateToDoUseCase {
    fun handle(request: UpdateToDoRequest)
}