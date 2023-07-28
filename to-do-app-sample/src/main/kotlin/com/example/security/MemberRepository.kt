package com.example.security

interface MemberRepository {
    fun nextId(): Id
    fun find(credential: Credential): Result<Member>
}