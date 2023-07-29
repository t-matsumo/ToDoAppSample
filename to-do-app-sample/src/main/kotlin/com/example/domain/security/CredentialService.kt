package com.example.domain.security


class CredentialService(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: PasswordEncoder) {
    fun canRegister(credential: Credential): Boolean {
        return credentialRepository.find(credential.id).isFailure
    }

    fun authenticate(credential: RequestCredential): Result<Id> {
        return credentialRepository
            .find(credential.id)
            .fold(
                onSuccess = {
                    if (passwordEncoder.matches(credential.password, it.password)) {
                        Result.success(it.id)
                    } else {
                        Result.failure(NoSuchElementException())
                    }
                },
                onFailure = { Result.failure(NoSuchElementException()) }
            )
    }
}