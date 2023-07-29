package com.example.infrastructure

import com.example.domain.security.*
import kotlin.NoSuchElementException

class CredentialRepositoryInMemory: CredentialRepository {
    private val dataStore = hashMapOf<Id, Credential>()

    override fun find(id: Id) = dataStore[id]
        .let { if (it != null) Result.success(it) else Result.failure(NoSuchElementException()) }

    override fun save(credential: Credential) {
        dataStore[credential.id] = credential
    }
}