package com.example.web.plugins

import com.example.web.todo.toDoRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.*

fun Application.configureRouting() {
    routing {
        get {
            call.respondRedirect("/todos")
        }
        get("sign-in") {
            call.respond(ThymeleafContent("sign-in", emptyMap()))
        }
        authenticate("auth-form") {
            post("sign-in") {
                val userName = call.principal<UserIdPrincipal>()?.name.toString()
                call.sessions.set(UserSession(name = userName, count = 1))
                call.respondRedirect("/todos")
            }
        }
    }
    toDoRoutes()
}
