package com.example.domain.security

import com.example.application.repository.CredentialRepository
import com.example.domain.security.credential.Credential
import com.example.domain.security.credential.Id
import com.example.domain.security.authenticaterequest.AuthenticateRequest


class CredentialService(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun canRegister(credential: Credential): Boolean {
        return credentialRepository.find(credential.name) == null
    }

    fun authenticate(credential: AuthenticateRequest): Id? {
        return credentialRepository
            .find(credential.name)
            ?.let {
                if (credential.name == it.name && passwordEncoder.matches(credential.password, it.password)) {
                    it.id
                } else {
                    null
                }
            }
    }
}