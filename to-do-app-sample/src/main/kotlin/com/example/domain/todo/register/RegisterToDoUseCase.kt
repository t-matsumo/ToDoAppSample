package com.example.domain.todo.register

data class RegisterToDoRequest(private val content: String) {
    fun content() = content
}

interface RegisterToDoUseCase {
    fun handle(request: RegisterToDoRequest)
}

