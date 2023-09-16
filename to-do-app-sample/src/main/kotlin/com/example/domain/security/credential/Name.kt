package com.example.domain.security.credential

@JvmInline
value class Name(val value: String) {
    init {
        require(value.length in 1..20)
        require(value.matches(Regex("^[a-zA-Z0-9_-]{1,20}$")))
    }
}