package com.example.application.usecase.security.register

class RegisterMemberRequest(val name: String, val password: String)

sealed interface RegisterMemberResponse
data object Success: RegisterMemberResponse
data class Failure(val reasons: FailureReason): RegisterMemberResponse

sealed interface FailureReason
data object NameShouldBeUniqueFailure: FailureReason

interface RegisterMemberUseCase {
    fun handle(request: RegisterMemberRequest): RegisterMemberResponse
}