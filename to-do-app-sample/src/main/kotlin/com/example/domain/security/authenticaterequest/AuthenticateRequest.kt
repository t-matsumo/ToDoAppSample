package com.example.domain.security.authenticaterequest

import com.example.domain.security.credential.Name

@JvmInline
value class Password(val value: String) {
    init {
        require(value.length in 1..1000)
    }
}

class AuthenticateRequest(val name: Name, val password: Password)