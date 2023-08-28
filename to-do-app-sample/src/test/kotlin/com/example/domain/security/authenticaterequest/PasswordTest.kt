package com.example.domain.security.authenticaterequest

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

class PasswordTest {
    @ParameterizedTest
    @MethodSource("validPasswordValueParameters")
    fun test_valid_password_value(value: String) {
        assertDoesNotThrow { Password(value) }
    }

    @ParameterizedTest
    @MethodSource("invalidPasswordValueParameters")
    fun test_invalid_password_value(value: String) {
        assertThrows<IllegalArgumentException> { Password(value) }
    }

    companion object {
        @JvmStatic
        fun validPasswordValueParameters() = arrayOf(
            Arguments.arguments("a"),
            Arguments.arguments("1".repeat(1000))
        )

        @JvmStatic
        fun invalidPasswordValueParameters() = arrayOf(
            Arguments.arguments(""),
            Arguments.arguments("1".repeat(1001)),
            Arguments.arguments("1".repeat(100000000))
        )
    }
}