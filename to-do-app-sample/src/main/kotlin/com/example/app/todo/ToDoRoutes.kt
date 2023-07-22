package com.example.app.todo

import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.find.*
import com.example.infrastructure.ToDoRepositoryInMemory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*

data class ToDoViewData(val id: String, val title: String, val content: String)

fun Application.toDoRoutes() {
    val toDoRepository: ToDoRepository = ToDoRepositoryInMemory()

    routing {
        val rootPathName = "/todos"
        route(rootPathName) {
            get {
                val findRecentToDoUseCase = FindRecentToDoInteractor(toDoRepository)
                val response = findRecentToDoUseCase.handle(FindRecentToDoRequest(10))

                val toDoViewDataList = response.toDos.map { ToDoViewData(it.id, it.title, it.content) }
                call.respond(ThymeleafContent("todo-list", mapOf("model" to toDoViewDataList)))
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


//                call.respondHtml {
//                    body {
//                        h1 { +toDo.title }
//                        p { +toDo.content }
//                    }
//                }
            }
        }
    }
}