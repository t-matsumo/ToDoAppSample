package com.example.app.plugins

import com.example.app.todo.toDoRoutes
import io.ktor.server.application.*

fun Application.configureRouting() {
    toDoRoutes()
}
