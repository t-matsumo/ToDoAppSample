package com.example.application.usecase.security.authenticate

import com.example.application.repository.CredentialRepository
import com.example.domain.security.*
import com.example.domain.security.credential.Name
import com.example.domain.security.authenticaterequest.Password
import com.example.domain.security.authenticaterequest.AuthenticateRequest as DomainAuthenticateRequest
class AuthenticateInteractor(
    credentialRepository: CredentialRepository,
    passwordEncoder: PasswordEncoder
): AuthenticateUseCase {
    private val credentialService = CredentialService(credentialRepository, passwordEncoder)

    override fun handle(request: AuthenticateRequest): AuthenticateResponse {
        val authenticateRequest = DomainAuthenticateRequest(
            Name(request.name),
            Password(request.password)
        )
        return AuthenticateResponse(credentialService.authenticate(authenticateRequest)?.value)
    }
}