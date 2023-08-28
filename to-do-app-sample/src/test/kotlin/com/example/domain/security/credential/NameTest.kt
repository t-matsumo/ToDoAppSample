package com.example.domain.security.credential

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource

class NameTest {
    @ParameterizedTest
    @MethodSource("validNameValueParameters")
    fun test_valid_name_value(value: String) {
        assertDoesNotThrow { Name(value) }
    }

    @ParameterizedTest
    @MethodSource("invalidNameValueParameters")
    fun test_invalid_name_value(value: String) {
        assertThrows<IllegalArgumentException> { Name(value) }
    }

    companion object {
        @JvmStatic
        fun validNameValueParameters() = arrayOf(
            arguments("a".repeat(1)), // min
            arguments("a".repeat(20)), // max
            arguments("aa1-_")
        )

        @JvmStatic
        fun invalidNameValueParameters() = arrayOf(
            arguments(""),
            arguments("a".repeat(21)),
            arguments("漢字"),
            arguments(" "),
            arguments("a".repeat(100000000))
        )
    }
}
