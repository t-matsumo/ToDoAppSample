package com.example.web

import com.example.application.repository.ToDoRepository
import com.example.application.usecase.security.authenticate.AuthenticateInteractor
import com.example.application.usecase.security.register.RegisterMemberInteractor
import com.example.application.usecase.security.register.RegisterMemberRequest
import com.example.domain.security.credential.Name
import com.example.domain.todo.*
import com.example.infrastructure.inmemory.CredentialRepositoryInMemory
import com.example.infrastructure.SpringSecurityPasswordEncoder
import com.example.infrastructure.inmemory.ToDoRepositoryInMemory
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

    val registerMemberUseCase = RegisterMemberInteractor(credentialRepository, passwordEncoder)

    // fixture
    registerMemberUseCase.handle(RegisterMemberRequest("admin", "password"))
    for (i in 0 until 10) {
        registerMemberUseCase.handle(RegisterMemberRequest("user$i", "password$i"))
    }

    for (i in 0..30) {
        val adminCredential = credentialRepository.find(Name("admin"))!!
        toDoRepository.save(Todo(toDoRepository.nextId(), OperatorId(adminCredential.id.value), ToDoTitle("title $i for ${adminCredential.name.value}"), ToDoContent("content $i"), ToDoCreatedAt.now()))
    }

    val userCredentials = List(10) {
        credentialRepository.find(Name("user$it"))!!
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
    configureRouting(toDoRepository, registerMemberUseCase)
}