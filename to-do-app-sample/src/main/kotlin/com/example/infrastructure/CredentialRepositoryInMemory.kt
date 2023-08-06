package com.example.infrastructure

import com.example.domain.security.*
import java.util.*
import kotlin.NoSuchElementException

class CredentialRepositoryInMemory: CredentialRepository {
    private val dataStore = hashMapOf<Id, Credential>()

    override fun nextId() = Id(UUID.randomUUID().toString())

    override fun find(name: Name) = dataStore.values.firstOrNull { it.name == name }

    override fun save(credential: Credential) {
        if (dataStore.values.any { it.id != credential.id && it.name == credential.name }) {
            throw RuntimeException("nameが重複！！！")
        }

        dataStore[credential.id] = credential
    }
}