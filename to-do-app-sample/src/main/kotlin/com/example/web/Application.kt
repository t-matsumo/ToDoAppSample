package com.example.web

import com.example.security.AuthenticateInteractor
import com.example.infrastructure.MemberRepositoryInMemory
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
    configureAuthentication(AuthenticateInteractor(MemberRepositoryInMemory()))
    configureThymeleaf()
    configureRouting()
}