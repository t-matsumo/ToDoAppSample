package com.example.web.plugins

import ch.qos.logback.core.encoder.EchoEncoder
import com.example.application.repository.ToDoRepository
import com.example.application.usecase.security.register.Failure
import com.example.application.usecase.security.register.RegisterMemberRequest
import com.example.application.usecase.security.register.RegisterMemberUseCase
import com.example.application.usecase.security.register.Success
import com.example.application.usecase.task.register.RegisterToDoRequest
import com.example.web.todo.toDoRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.*
import java.lang.Exception
import java.lang.reflect.Executable
import java.sql.SQLException
import java.util.concurrent.atomic.AtomicInteger

fun Application.configureRouting(
    toDoRepository: ToDoRepository,
    registerMemberUseCase: RegisterMemberUseCase
) {
    routing {
        get {
            call.respondRedirect("/todos")
        }
        get("sign-in") {
            call.respond(ThymeleafContent("sign-in", emptyMap()))
        }
        get ("sign-up") {
            call.respond(ThymeleafContent("sign-up", emptyMap()))
        }
        post("sign-up") {
            val formParameters = call.receiveParameters()
            val username = formParameters["username"]
            val password = formParameters["password"]
            if (username == null || password == null) {
                call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                return@post
            }

            val result = registerMemberUseCase.handle(RegisterMemberRequest(username, password))
            when (result) {
                is Failure -> {
                    call.respondRedirect("sign-up?error=duplicated-name")
                }
                Success -> call.respondRedirect("sign-in?success=register-user")
            }
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