package com.example.infrastructure.inmemory

import com.example.application.repository.CredentialRepository
import com.example.domain.security.credential.Credential
import com.example.domain.security.credential.Id
import com.example.domain.security.credential.Name
import java.util.*
import kotlin.collections.HashMap

private val globalDataStore = hashMapOf<Id, Credential>()

class CredentialRepositoryInMemory(
    private val dataStore: HashMap<Id, Credential> = globalDataStore
): CredentialRepository {
    override fun nextId() = Id(UUID.randomUUID().toString())

    override fun find(name: Name) = dataStore.values.firstOrNull { it.name == name }

    @Synchronized
    override fun save(credential: Credential) {
        if (dataStore.values.any { it.id != credential.id && it.name == credential.name }) {
            throw RuntimeException("nameが重複！！！")
        }

        dataStore[credential.id] = credential
    }
}