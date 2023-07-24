package com.example.domain.security.authenticate

@JvmInline
value class Id(val value: String)

@JvmInline
value class Name(val value: String)

@JvmInline
value class Password(val value: String)

data class Credential(val name: Name, val password: Password)

class Member(val id: Id, val credential: Credential)