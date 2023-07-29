package com.example.domain.security

interface PasswordEncoder {
    fun encode(rawPassword: Password): EncryptedPassword
    fun matches(password: Password, encryptedPassword: EncryptedPassword): Boolean
}