package com.example.infrastructure

import com.example.security.*
import java.util.*
import kotlin.NoSuchElementException

class MemberRepositoryInMemory: MemberRepository {
    private val dataStore = hashMapOf<Credential, Member>()

    init {
        val adminCredential = Credential(Name("admin"), Password("pass"))
        dataStore[adminCredential] = Member(nextId(), adminCredential)
    }

    override fun nextId() =  Id(UUID.randomUUID().toString())

    override fun find(credential: Credential) = dataStore[credential]
        .let { if (it != null) Result.success(it) else Result.failure(NoSuchElementException()) }
}