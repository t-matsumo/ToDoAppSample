package com.example.domain.security

@JvmInline
value class Password(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

class RequestCredential(val name: Name, val password: Password)