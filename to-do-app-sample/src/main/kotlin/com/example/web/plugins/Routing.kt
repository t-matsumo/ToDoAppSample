package com.example.web.plugins

import ch.qos.logback.core.encoder.EchoEncoder
import com.example.application.repository.ToDoRepository
import com.example.web.todo.toDoRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.*
import java.lang.Exception
import java.lang.reflect.Executable
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger

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

//  ab -n 50 -c 50 http://localhost:8080/test