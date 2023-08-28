package com.example.web

import com.example.application.repository.ToDoRepository
import com.example.application.usecase.security.authenticate.AuthenticateInteractor
import com.example.domain.security.credential.Credential
import com.example.domain.security.credential.Name
import com.example.domain.security.authenticaterequest.Password
import com.example.domain.todo.*
import com.example.infrastructure.inmemory.CredentialRepositoryInMemory
import com.example.infrastructure.SpringSecurityPasswordEncoder
import com.example.infrastructure.inmemory.ToDoRepositoryInMemory
import com.example.web.plugins.configureAuthentication
import com.example.web.plugins.configureResources
import com.example.web.plugins.configureRouting
import com.example.web.plugins.configureThymeleaf
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val passwordEncoder = SpringSecurityPasswordEncoder()

    val credentialRepository = CredentialRepositoryInMemory()

    val toDoRepository: ToDoRepository = ToDoRepositoryInMemory()

    // fixture
    val adminCredential = Credential(credentialRepository.nextId(), Name("admin"), passwordEncoder.encode(Password("password")))
    val userCredentials = List(10) {
        Credential(
            credentialRepository.nextId(),
            Name("user$it"),
            passwordEncoder.encode(Password("Password$it"))
        )
    }
    credentialRepository.save(adminCredential)
    for (c in userCredentials) {
        credentialRepository.save(c)
    }

    for (i in 0..30) {
        toDoRepository.save(Todo(toDoRepository.nextId(), OperatorId(adminCredential.id.value), ToDoTitle("title $i for ${adminCredential.name.value}"), ToDoContent("content $i"), ToDoCreatedAt.now()))
    }

    for (credential in userCredentials) {
        for (i in 0..30) {
            toDoRepository.save(
                Todo(
                    toDoRepository.nextId(),
                    OperatorId(credential.id.value),
                    ToDoTitle("title $i for ${credential.name.value}"),
                    ToDoContent("content $i"),
                    ToDoCreatedAt.now()
                )
            )
        }
    }

    configureAuthentication(AuthenticateInteractor(credentialRepository, passwordEncoder))
    configureThymeleaf()
    configureResources()
    configureRouting(toDoRepository)
}