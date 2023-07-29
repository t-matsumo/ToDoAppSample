package com.example.web

import com.example.domain.security.Credential
import com.example.domain.security.Id
import com.example.domain.security.Password
import com.example.domain.security.PasswordEncoder
import com.example.domain.security.authenticate.AuthenticateInteractor
import com.example.domain.todo.*
import com.example.infrastructure.CredentialRepositoryInMemory
import com.example.infrastructure.SpringSecurityPasswordEncoder
import com.example.infrastructure.ToDoRepositoryInMemory
import com.example.web.plugins.configureAuthentication
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
    val adminCredential = Credential(Id("admin"), passwordEncoder.encode(Password("password")))
    val userCredentials = List(10) {  Credential(Id("user$it"), passwordEncoder.encode(Password("Password$it"))) }
    credentialRepository.save(adminCredential)
    for (c in userCredentials) {
        credentialRepository.save(c)
    }

    for (i in 0..30) {
        toDoRepository.save(Todo(toDoRepository.nextId(), AuthorId(adminCredential.id.value), ToDoTitle("title $i for ${adminCredential.id.value}"), ToDoContent("content $i"), ToDoCreatedAt.now()))
    }

    for (credential in userCredentials) {
        for (i in 0..30) {
            toDoRepository.save(
                Todo(
                    toDoRepository.nextId(),
                    AuthorId(credential.id.value),
                    ToDoTitle("title $i for ${credential.id.value}"),
                    ToDoContent("content $i"),
                    ToDoCreatedAt.now()
                )
            )
        }
    }

    configureAuthentication(AuthenticateInteractor(credentialRepository, passwordEncoder))
    configureThymeleaf()
    configureRouting(toDoRepository)
}