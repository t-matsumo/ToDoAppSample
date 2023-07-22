package com.example.app.todo

import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.delete.DeleteToDoInteractor
import com.example.domain.todo.delete.DeleteToDoRequest
import com.example.domain.todo.find.*
import com.example.domain.todo.register.RegisterToDoInteractor
import com.example.domain.todo.register.RegisterToDoRequest
import com.example.domain.todo.update.UpdateToDoInteractor
import com.example.domain.todo.update.UpdateToDoRequest
import com.example.infrastructure.ToDoRepositoryInMemory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

data class ToDoViewData(val id: String, val title: String, val content: String)

fun Application.toDoRoutes() {
    val toDoRepository: ToDoRepository = ToDoRepositoryInMemory()

    routing {
        val rootPathName = "/todos"
        route(rootPathName) {
            post {
                val formParameters = call.receiveParameters()
                val title = formParameters["title"]
                val content = formParameters["content"]
                if (title == null || content == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                val registerToDoUseCase = RegisterToDoInteractor(toDoRepository)
                registerToDoUseCase.handle(RegisterToDoRequest(title, content))

                call.respondRedirect(rootPathName)
            }
            get {
                val findRecentToDoUseCase = FindRecentToDoInteractor(toDoRepository)
                val response = findRecentToDoUseCase.handle(FindRecentToDoRequest(10))

                val toDoViewDataList = response.toDos.map { ToDoViewData(it.id, it.title, it.content) }
                call.respond(ThymeleafContent("todo-list", mapOf("toDoViewDataList" to toDoViewDataList)))
            }
            get("/{toDoId}") {
                val toDoId = call.parameters["toDoId"]
                if (toDoId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@get
                }

                val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
                val toDo = findToDoByIdUseCase
                    .handle(FindToDoByIdRequest(toDoId))
                    .toDo
                    .getOrNull()
                if (toDo == null) {
                    call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
                    return@get
                }

                call.respond(ThymeleafContent("todo", mapOf("model" to toDo)))
            }
            get("/{toDoId}/edit") {
                val toDoId = call.parameters["toDoId"]
                if (toDoId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@get
                }

                val findToDoByIdUseCase = FindToDoByIdInteractor(toDoRepository)
                val toDo = findToDoByIdUseCase
                    .handle(FindToDoByIdRequest(toDoId))
                    .toDo
                    .getOrNull()
                if (toDo == null) {
                    call.respond(HttpStatusCode.NotFound, HttpStatusCode.NotFound.toString())
                    return@get
                }

                call.respond(ThymeleafContent("todo-edit", mapOf("model" to toDo)))
            }
            post("/{toDoId}") {
                val toDoId = call.parameters["toDoId"]
                val formParameters = call.receiveParameters()
                val title = formParameters["title"]
                val content = formParameters["content"]
                if (toDoId == null || title == null || content == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                val updateToDoUseCase = UpdateToDoInteractor(toDoRepository)
                updateToDoUseCase.handle(UpdateToDoRequest(toDoId, title, content))

                call.respondRedirect(rootPathName)
            }
            post("/{toDoId}/delete") {
                val toDoId = call.parameters["toDoId"]
                if (toDoId == null) {
                    call.respond(HttpStatusCode.BadRequest, HttpStatusCode.BadRequest.toString())
                    return@post
                }

                val deleteToDoUseCase = DeleteToDoInteractor(toDoRepository)
                deleteToDoUseCase.handle(DeleteToDoRequest(toDoId))
                call.respondRedirect(rootPathName)
            }
        }
    }
}