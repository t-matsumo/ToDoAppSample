package com.example.domain.security

import com.example.application.repository.CredentialRepository
import com.example.domain.security.authenticaterequest.AuthenticateRequest
import com.example.domain.security.authenticaterequest.Password
import com.example.domain.security.credential.Credential
import com.example.domain.security.credential.Id
import com.example.domain.security.credential.Name
import com.example.infrastructure.SpringSecurityPasswordEncoder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class CredentialServiceTest {
    private lateinit var credentialService: CredentialService
    private lateinit var credentialRepositoryMock: CredentialRepositoryMock
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setup() {
        credentialRepositoryMock = CredentialRepositoryMock()
        passwordEncoder = SpringSecurityPasswordEncoder()
        credentialService = CredentialService(
            credentialRepositoryMock,
            passwordEncoder
        )
    }

    @Nested
    inner class CanRegisterTest {
        @Test
        fun unique_name_can_be_registered() {
            credentialRepositoryMock.findByNameResult = null

            val credential = Credential(
                Id(UUID.randomUUID().toString()),
                Name("name"),
                passwordEncoder.encode(Password("password"))
            )

            Assertions.assertTrue(credentialService.canRegister(credential))
        }

        @Test
        fun duplicated_name_can_not_be_registered() {
            credentialRepositoryMock.findByNameResult = Credential(
                Id(UUID.randomUUID().toString()),
                Name("same_name"),
                passwordEncoder.encode(Password("password"))
            )

            val credential = Credential(
                Id(UUID.randomUUID().toString()),
                Name("same_name"),
                passwordEncoder.encode(Password("other password"))
            )

            Assertions.assertFalse(credentialService.canRegister(credential))
        }
    }

    @Nested
    inner class AuthenticateTest {
        @Test
        fun given_registered_credential_then_return_Id() {
            credentialRepositoryMock.findByNameResult = Credential(
                Id(UUID.randomUUID().toString()),
                Name("name"),
                passwordEncoder.encode(Password("password"))
            )

            val request = AuthenticateRequest(Name("name"), Password("password"))
            Assertions.assertNotNull( credentialService.authenticate(request))
        }

        @Test
        fun given_unregistered_name_then_return_null() {
            credentialRepositoryMock.findByNameResult = null

            val request = AuthenticateRequest(Name("name"), Password("password"))
            Assertions.assertNull( credentialService.authenticate(request))
        }

        @Test
        fun given_wrong_password_then_return_null() {
            credentialRepositoryMock.findByNameResult = Credential(
                Id(UUID.randomUUID().toString()),
                Name("name"),
                passwordEncoder.encode(Password("password"))
            )

            val request = AuthenticateRequest(Name("name"), Password("wrong password"))
            Assertions.assertNull( credentialService.authenticate(request))
        }
    }
}

private class CredentialRepositoryMock: CredentialRepository {
    var findByNameResult: Credential? = null

    override fun nextId(): Id {
        TODO("Not yet implemented")
    }

    override fun find(name: Name): Credential? {
        return findByNameResult
    }

    override fun save(credential: Credential) {
        TODO("Not yet implemented")
    }
}

