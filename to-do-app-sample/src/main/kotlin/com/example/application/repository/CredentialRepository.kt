package com.example.application.repository

import com.example.domain.security.credential.Credential
import com.example.domain.security.credential.Id
import com.example.domain.security.credential.Name

interface CredentialRepository {
    fun nextId(): Id
    fun find(name: Name): Credential?

    // note: 実装時、Credential.idとCredential.nameはUnique制約を付ける。
    fun save(credential: Credential)
}