package com.example.web.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.Authentication
import io.ktor.server.response.*
import io.ktor.server.sessions.*

data class UserSession(val name: String, val count: Int) : Principal

fun Application.configureAuthentication() {
    install(Sessions) {
        cookie<UserSession>("user_session") {
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
                if (credentials.name == "admin" && credentials.password == "password") {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
            challenge {
                call.respond(HttpStatusCode.Unauthorized, "Credentials are not valid")
            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                if(session.name.startsWith("admin")) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/sign-in")
            }
        }
    }
}