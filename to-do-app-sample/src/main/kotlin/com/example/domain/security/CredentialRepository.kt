package com.example.domain.security

interface CredentialRepository {
    fun find(id: Id): Result<Credential>
    fun save(credential: Credential)
}