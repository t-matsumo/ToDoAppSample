package com.example.infrastructure

import com.example.domain.security.credential.EncryptedPassword
import com.example.domain.security.authenticaterequest.Password
import com.example.domain.security.PasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories

class SpringSecurityPasswordEncoder: PasswordEncoder {
    private val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    override fun encode(rawPassword: Password): EncryptedPassword {
        return object: EncryptedPassword(encoder.encode(rawPassword.value)) {}
    }

    override fun matches(password: Password, encryptedPassword: EncryptedPassword): Boolean {
        return encoder.matches(password.value, encryptedPassword.value)
    }
}