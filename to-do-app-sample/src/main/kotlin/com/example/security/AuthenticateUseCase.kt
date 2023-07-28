package com.example.security

class AuthenticateNoSuchMemberException: NoSuchElementException()

data class AuthenticateRequest(val name: String, val password: String)
data class AuthenticateResponse(val result: Result<Id>)

interface AuthenticateUseCase {
    fun handle(request: AuthenticateRequest): AuthenticateResponse
}