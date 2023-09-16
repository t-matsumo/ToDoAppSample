package com.example.application.usecase.security.register

import com.example.application.repository.CredentialRepository
import com.example.domain.security.PasswordEncoder
import com.example.domain.security.authenticaterequest.Password
import com.example.domain.security.credential.Name
import com.example.infrastructure.SpringSecurityPasswordEncoder
import com.example.infrastructure.inmemory.CredentialRepositoryInMemory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class RegisterMemberInteractorTest {
    private lateinit var registerMemberUseCase: RegisterMemberUseCase
    private lateinit var credentialRepository: CredentialRepository
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setup() {
        credentialRepository = CredentialRepositoryInMemory(hashMapOf())
        passwordEncoder = SpringSecurityPasswordEncoder()
        registerMemberUseCase = RegisterMemberInteractor(
            credentialRepository,
            passwordEncoder
        )
    }

    @Test
    fun register_with_request() {
        val request = RegisterMemberRequest("name", "password")
        val result = registerMemberUseCase.handle(request)

        val actual = credentialRepository.find(Name("name"))!!

        Assertions.assertEquals(Success, result)
        Assertions.assertEquals("name", actual.name.value)
        Assertions.assertTrue(passwordEncoder.matches(Password("password"), actual.password))
    }

    @Test
    fun register_fail_with_duplicated_name() {
        val request = RegisterMemberRequest("name", "password")
        registerMemberUseCase.handle(request)

        val requestDuplicatedName = RegisterMemberRequest("name", "other_password")
        val result = registerMemberUseCase.handle(requestDuplicatedName)
        Assertions.assertEquals(Failure(NameShouldBeUniqueFailure), result)
    }

    @Test
    fun inValid_name_causes_exception() {
        val request = RegisterMemberRequest("", "password")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            registerMemberUseCase.handle(request)
        }
    }

    @Test
    fun inValid_password_causes_exception() {
        val request = RegisterMemberRequest("name", "")
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            registerMemberUseCase.handle(request)
        }
    }
}