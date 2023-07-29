package com.example.domain.security.register

class RegisterMemberRequest(val name: String, val password: String)

interface RegisterMemberUseCase {
    fun handle(request: RegisterMemberRequest)
}