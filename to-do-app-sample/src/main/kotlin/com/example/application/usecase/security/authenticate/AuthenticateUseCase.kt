package com.example.application.usecase.security.authenticate

data class AuthenticateRequest(val name: String, val password: String)
data class AuthenticateResponse(val id: String?)

interface AuthenticateUseCase {
    fun handle(request: AuthenticateRequest): AuthenticateResponse
}