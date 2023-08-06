package com.example.domain.security


class CredentialService(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun canRegister(credential: Credential): Boolean {
        return credentialRepository.find(credential.name) == null
    }

    fun authenticate(credential: RequestCredential): Id? {
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