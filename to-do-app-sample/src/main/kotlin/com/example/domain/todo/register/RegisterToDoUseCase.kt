package com.example.domain.todo.register

data class RegisterToDoRequest(val memberId: String, val title: String, val content: String)

interface RegisterToDoUseCase {
    fun handle(request: RegisterToDoRequest)
}

