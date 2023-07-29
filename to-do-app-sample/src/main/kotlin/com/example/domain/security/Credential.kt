package com.example.domain.security

@JvmInline
value class Id(val value: String) {
    init {
        require(value.isNotEmpty())
    }
}

interface EncryptedPassword {
    val value: String
}

class Credential(val id: Id, val password: EncryptedPassword)