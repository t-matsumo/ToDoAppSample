package com.example.web.todo

import com.example.application.repository.ToDoRepository
import com.example.application.usecase.task.delete.DeleteToDoInteractor
import com.example.application.usecase.task.delete.DeleteToDoRequest
import com.example.application.usecase.task.find.FindRecentToDoInteractor
import com.example.application.usecase.task.find.FindRecentToDoRequest
import com.example.application.usecase.task.find.FindToDoByIdInteractor
import com.example.application.usecase.task.find.FindToDoByIdRequest
import com.example.application.usecase.task.register.RegisterToDoInteractor
import com.example.application.usecase.task.register.RegisterToDoRequest
import com.example.application.usecase.task.update.UpdateToDoInteractor
import com.example.application.usecase.task.update.UpdateToDoRequest
import com.example.infrastructure.inmemory.InMemoryFindRecentToDoQuery
import com.example.web.plugins.ToDoForWeb
import com.example.web.plugins.UserSession
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.post
import io.ktor.server.thymeleaf.*

data class ToDoViewData(val id: String, val title: String, val content: String)

fun Application.toDoRoutes(toDoRepository: ToDoRepository) {
    val registerToDoUseCase = RegisterToDoInteractor(toDoRepository)
    val findRecentToDoUseCase = FindRecentToDoInteractor(InMemoryFindRecentToDoQuery())
    val deleteToDoUseCase = DeleteToDoInteractor(toDoRepository)

//    routing {
//        authenticate("auth-session") {
//            val rootPathName = "/todos"
//            route(rootPathName) {
//
//                post {
//                    val formParameters = call.receiveParameters()
//                    val title = formParameters["title"]
//                    val content = formParameters["content"]
//                    val memberId = call.principal<UserSession>()?.memberId
//                    if (title == null || content == null || memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@post
//                    }
//
//                    registerToDoUseCase.handle(RegisterToDoRequest(memberId, title, content))
//                    call.respondRedirect(rootPathName)
//                }
//                get {
//                    val memberId = call.principal<UserSession>()?.memberId
//                    if (memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@get
//                    }
//                    val response = findRecentToDoUseCase.handle(FindRecentToDoRequest(memberId, 10))
//
//                    val toDoViewDataList = response.toDos.map { ToDoViewData(it.id, it.title, it.content) }
//                    call.respond(ThymeleafContent("todo-list", mapOf("toDoViewDataList" to toDoViewDataList)))
//                }
//                get("/{toDoId}") {
//                    val memberId = call.principal<UserSession>()?.memberId
//                    val toDoId = call.parameters["toDoId"]
//                    if (toDoId == null || memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@get
//                    }
//
//                    val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
//                    val toDo = findToDoByIdUseCase
//                        .handle(FindToDoByIdRequest(memberId, toDoId))
//                        .toDo
//                    if (toDo == null) {
//                        call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
//                        return@get
//                    }
//
//                    call.respond(ThymeleafContent("todo", mapOf("model" to toDo)))
//                }
//                get("/{toDoId}/edit") {
//                    val memberId = call.principal<UserSession>()?.memberId
//                    val toDoId = call.parameters["toDoId"]
//                    if (toDoId == null || memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@get
//                    }
//
//                    val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
//                    val toDo = findToDoByIdUseCase
//                        .handle(FindToDoByIdRequest(memberId, toDoId))
//                        .toDo
//                    if (toDo == null) {
//                        call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
//                        return@get
//                    }
//
//                    call.respond(ThymeleafContent("todo-edit", mapOf("model" to toDo)))
//                }
//                post("/{toDoId}") {
//                    val memberId = call.principal<UserSession>()?.memberId
//                    val toDoId = call.parameters["toDoId"]
//                    val formParameters = call.receiveParameters()
//                    val title = formParameters["title"]
//                    val content = formParameters["content"]
//                    if (toDoId == null || title == null || content == null || memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@post
//                    }
//
//                    val updateToDoUseCase = UpdateToDoInteractor(toDoRepository)
//                    updateToDoUseCase.handle(UpdateToDoRequest(memberId, toDoId, title, content))
//
//                    call.respondRedirect(rootPathName)
//                }
//                post("/{toDoId}/delete") {
//                    val memberId = call.principal<UserSession>()?.memberId
//                    val toDoId = call.parameters["toDoId"]
//                    if (toDoId == null || memberId == null) {
//                        call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
//                        return@post
//                    }
//
//                    deleteToDoUseCase.handle(DeleteToDoRequest(memberId, toDoId))
//                    call.respondRedirect(rootPathName)
//                }
//            }
//        }

    routing {
        authenticate("auth-session") {
            post<ToDoForWeb> {
                val memberId = call.principal<UserSession>()?.memberId
                val formParameters = call.receiveParameters()
                val title = formParameters["title"]
                val content = formParameters["content"]
                if (title == null || content == null || memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                registerToDoUseCase.handle(RegisterToDoRequest(memberId, title, content))
                call.respondRedirect(this@toDoRoutes.href(ToDoForWeb()))
            }
            get<ToDoForWeb> {
                val memberId = call.principal<UserSession>()?.memberId
                if (memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@get
                }
                val response = findRecentToDoUseCase.handle(FindRecentToDoRequest(memberId, 10))

                val toDoViewDataList = response.toDos.map { ToDoViewData(it.id, it.title, it.content) }
                call.respond(ThymeleafContent("todo-list", mapOf("toDoViewDataList" to toDoViewDataList)))
            }
            get<ToDoForWeb.Id> {
                val memberId = call.principal<UserSession>()?.memberId
                if (memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@get
                }

                val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
                val toDo = findToDoByIdUseCase
                    .handle(FindToDoByIdRequest(memberId, it.id))
                    .toDo
                if (toDo == null) {
                    call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
                    return@get
                }

                call.respond(ThymeleafContent("todo", mapOf("model" to toDo)))
            }
            get<ToDoForWeb.Id.Edit> {
                val memberId = call.principal<UserSession>()?.memberId
                if (memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@get
                }

                val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
                val toDo = findToDoByIdUseCase
                    .handle(FindToDoByIdRequest(memberId, it.parent.id))
                    .toDo
                if (toDo == null) {
                    call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
                    return@get
                }

                call.respond(ThymeleafContent("todo-edit", mapOf("model" to toDo)))
            }
            post<ToDoForWeb.Id> {
                val memberId = call.principal<UserSession>()?.memberId
                val formParameters = call.receiveParameters()
                val title = formParameters["title"]
                val content = formParameters["content"]
                if (title == null || content == null || memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                val updateToDoUseCase = UpdateToDoInteractor(toDoRepository)
                updateToDoUseCase.handle(UpdateToDoRequest(memberId, it.id, title, content))

                call.respondRedirect(this@toDoRoutes.href(ToDoForWeb()))
            }
            post<ToDoForWeb.Id.Delete> {
                val memberId = call.principal<UserSession>()?.memberId
                if (memberId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                deleteToDoUseCase.handle(DeleteToDoRequest(memberId, it.parent.id))
                call.respondRedirect(this@toDoRoutes.href(ToDoForWeb()))
            }
        }
    }
}