package com.example.security

class AuthenticateInteractor(private val memberRepository: MemberRepository): AuthenticateUseCase {
    override fun handle(request: AuthenticateRequest): AuthenticateResponse {
        return memberRepository
            .find(Credential(Name(request.name), Password(request.password)))
            .fold(
                onSuccess = { Result.success(it.id) },
                onFailure = { Result.failure(AuthenticateNoSuchMemberException()) }
            )
            .let { AuthenticateResponse(it) }
    }
}