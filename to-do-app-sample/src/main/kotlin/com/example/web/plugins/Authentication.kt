package com.example.web.plugins

import com.example.security.AuthenticateRequest
import com.example.security.AuthenticateUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.Authentication
import io.ktor.server.response.*
import io.ktor.server.sessions.*

data class UserSession(val memberId: String, val count: Int) : Principal

fun Application.configureAuthentication(authenticateUseCase: AuthenticateUseCase) {
    install(Sessions) {
        cookie<UserSession>("user_session", SessionStorageMemory()) {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 60
            // cookie.secure = true
            cookie.extensions["SameSite"] = "strict"
        }
    }
    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                authenticateUseCase
                    .handle(AuthenticateRequest(credentials.name, credentials.password))
                    .result
                    .fold(
                        onSuccess = { UserIdPrincipal(it.value) },
                        onFailure = { null }
                    )
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Credentials are not valid")
            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                 session
            }
            challenge {
                call.respondRedirect("/sign-in")
            }
        }
    }
}