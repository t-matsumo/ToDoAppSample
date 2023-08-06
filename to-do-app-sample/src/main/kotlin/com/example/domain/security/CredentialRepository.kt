package com.example.domain.security

interface CredentialRepository {
    fun nextId(): Id
    fun find(name: Name): Credential?

    // note: 実装時、Credential.idとCredential.nameはUnique制約を付ける。
    fun save(credential: Credential)
}