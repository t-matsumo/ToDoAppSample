package com.example.domain.security

import com.example.domain.security.credential.EncryptedPassword
import com.example.domain.security.authenticaterequest.Password

interface PasswordEncoder {
    fun encode(rawPassword: Password): EncryptedPassword
    fun matches(password: Password, encryptedPassword: EncryptedPassword): Boolean
}