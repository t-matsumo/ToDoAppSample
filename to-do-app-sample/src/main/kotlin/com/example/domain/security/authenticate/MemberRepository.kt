package com.example.domain.security.authenticate

interface MemberRepository {
    fun nextId(): Id
    fun find(credential: Credential): Result<Member>
}