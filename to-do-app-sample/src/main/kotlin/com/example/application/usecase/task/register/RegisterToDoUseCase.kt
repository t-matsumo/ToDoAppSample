package com.example.application.usecase.task.register

data class RegisterToDoRequest(val memberId: String, val title: String, val content: String)

interface RegisterToDoUseCase {
    fun handle(request: RegisterToDoRequest)
}

