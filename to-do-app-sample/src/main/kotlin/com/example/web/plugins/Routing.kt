package com.example.web.plugins

import com.example.domain.todo.ToDoRepository
import com.example.web.todo.toDoRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.*

fun Application.configureRouting(toDoRepository: ToDoRepository) {
    routing {
        get {
            call.respondRedirect("/todos")
        }
        get("sign-in") {
            call.respond(ThymeleafContent("sign-in", emptyMap()))
        }
        authenticate("auth-form") {
            post("sign-in") {
                val memberId = call.principal<UserIdPrincipal>()?.name.toString()
                call.sessions.clear<UserSession>()
                call.sessions.set(UserSession(memberId))
                call.respondRedirect("/todos")
            }
        }
    }
    toDoRoutes(toDoRepository)
}
