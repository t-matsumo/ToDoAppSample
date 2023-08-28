package com.example.domain.security.credential

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class IdTest {
    @ParameterizedTest
    @MethodSource("validIdValueParameters")
    fun test_valid_Id_value(value: String) {
        assertDoesNotThrow { Id(value) }
    }

    @ParameterizedTest
    @MethodSource("invalidIdValueParameters")
    fun test_invalid_Id_value(value: String) {
        assertThrows<IllegalArgumentException> { Id(value) }
    }

    companion object {
        @JvmStatic
        fun validIdValueParameters() = arrayOf(
            arguments("a".repeat(36)),
            arguments("1".repeat(35) + "-")
        )

        @JvmStatic
        fun invalidIdValueParameters() = arrayOf(
            arguments("a".repeat(35)),
            arguments("a".repeat(37)),
            arguments(" ".repeat(36)),
            arguments("a".repeat(100000000))
        )
    }
}
