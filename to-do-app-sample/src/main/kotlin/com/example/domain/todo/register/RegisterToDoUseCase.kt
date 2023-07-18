package com.example.domain.todo.register

data class RegisterToDoRequest(val content: String)

interface RegisterToDoUseCase {
    fun handle(request: RegisterToDoRequest)
}

