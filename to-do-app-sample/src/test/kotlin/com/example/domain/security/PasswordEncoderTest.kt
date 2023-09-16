package com.example.domain.security

import com.example.domain.security.authenticaterequest.Password
import com.example.infrastructure.SpringSecurityPasswordEncoder
import org.junit.jupiter.api.*

abstract class PasswordEncoderTest {
    lateinit var encoder: PasswordEncoder
    val forMessage: String get() = "for ${encoder.javaClass.name}"

    @Nested
    inner class EncodeTest {
        @Test
        fun created_encrypted_password_is_hashed() {
            val encryptedPassword = encoder.encode(Password("password"))
            Assertions.assertNotEquals("password", encryptedPassword.value, forMessage)
        }
    }

    @Nested
    inner class MatchesTest {
        @Test
        fun encryptedPassword_can_match_correct_password() {
            val encryptedPassword = encoder.encode(Password("password"))
            Assertions.assertTrue(encoder.matches(Password("password"), encryptedPassword), forMessage)
        }

        @Test
        fun encryptedPassword_can_not_match_incorrect_password() {
            val encryptedPassword = encoder.encode(Password("password"))
            Assertions.assertFalse(encoder.matches(Password("wrong password"), encryptedPassword), forMessage)
        }
    }
}

class SpringSecurityPasswordEncoderTest: PasswordEncoderTest() {
    @BeforeEach
    fun setup() {
        encoder = SpringSecurityPasswordEncoder()
    }
}