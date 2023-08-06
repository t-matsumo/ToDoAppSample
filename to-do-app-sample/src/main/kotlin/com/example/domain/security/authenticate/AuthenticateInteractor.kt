package com.example.domain.security.authenticate

import com.example.domain.security.*

class AuthenticateInteractor(
    credentialRepository: CredentialRepository,
    passwordEncoder: PasswordEncoder
): AuthenticateUseCase {
    private val credentialService = CredentialService(credentialRepository, passwordEncoder)

    override fun handle(request: AuthenticateRequest): AuthenticateResponse {
        val requestCredential = RequestCredential(Name(request.name), Password(request.password))
        return AuthenticateResponse(credentialService.authenticate(requestCredential)?.value)
    }
}