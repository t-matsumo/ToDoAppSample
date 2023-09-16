package com.example.domain.security.credential

@JvmInline
value class Id(val value: String) {
    init {
        require(value.length == 36)
        require(value.matches(Regex("^[a-zA-Z0-9-]{36}$")))
    }
}