package com.example.domain.security

@JvmInline
value class Password(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

class RequestCredential(val id: Id, val password: Password)