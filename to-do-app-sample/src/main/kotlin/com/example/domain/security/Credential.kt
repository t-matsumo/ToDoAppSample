package com.example.domain.security

@JvmInline
value class Id(val value: String) {
    init {
        require(value.trim().isNotEmpty())
    }
}

@JvmInline
value class Name(val value: String) {
    init {
        require(value.trim().isNotEmpty())
    }
}

interface EncryptedPassword {
    val value: String
}

class Credential(val id: Id, val name: Name, val password: EncryptedPassword)