package com.example.domain.security.credential

abstract class EncryptedPassword(val value: String) {
    init {
        require(value.length in 1..10000)
    }
}