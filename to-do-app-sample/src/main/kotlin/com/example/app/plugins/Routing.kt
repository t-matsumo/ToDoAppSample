package com.example.app.plugins

import com.example.app.todo.toDoRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get {
            call.respondRedirect("/todos")
        }
    }
    toDoRoutes()
}
