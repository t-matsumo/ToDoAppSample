package com.example.domain.security.register

import com.example.domain.security.*

class RegisterMemberInteractor(
    private val credentialRepository: CredentialRepository,
    private val passwordEncoder: PasswordEncoder,
): RegisterMemberUseCase {
    private val credentialService = CredentialService(credentialRepository, passwordEncoder)

    override fun handle(request: RegisterMemberRequest) {
        val credential = Credential(
            Id(request.name),
            passwordEncoder.encode(Password(request.password))
        )

        if (credentialService.canRegister(credential)) {
            credentialRepository.save(credential)
        }
    }
}
