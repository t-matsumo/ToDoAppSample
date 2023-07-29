package com.example.infrastructure

import com.example.domain.security.EncryptedPassword
import com.example.domain.security.Password
import com.example.domain.security.PasswordEncoder
import org.springframework.security.crypto.factory.PasswordEncoderFactories

class SpringSecurityPasswordEncoder: PasswordEncoder {
    private val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    override fun encode(rawPassword: Password): EncryptedPassword {
        return object: EncryptedPassword {
            override val value = encoder.encode(rawPassword.value)
        }
    }

    override fun matches(password: Password, encryptedPassword: EncryptedPassword): Boolean {
        return encoder.matches(password.value, encryptedPassword.value)
    }
}