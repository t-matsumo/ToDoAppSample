package com.example.domain.security.register

import com.example.domain.security.*

class RegisterMemberInteractor(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: PasswordEncoder,
): RegisterMemberUseCase {
    private val credentialService = CredentialService(credentialRepository, passwordEncoder)

    override fun handle(request: RegisterMemberRequest): RegisterMemberResponse {
        val credential = Credential(
            credentialRepository.nextId(),
            Name(request.name),
            passwordEncoder.encode(Password(request.password))
        )

        if (!credentialService.canRegister(credential)) {
            // 今のところこれしかないので手抜き
            return Failure(NameShouldBeUniqueFailure)
        }

        credentialRepository.save(credential)
        return Success
    }
}
