package com.example.app.todo

import com.example.domain.todo.find.FindRecentToDoInteractor
import com.example.domain.todo.find.FindRecentToDoRequest
import com.example.infrastructure.ToDoRepositoryInMemory
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.toDoRoutes() {
    val findRecentToDoUseCase = FindRecentToDoInteractor(ToDoRepositoryInMemory())

    routing {
        get("/todos") {
            val response = findRecentToDoUseCase.handle(FindRecentToDoRequest(10))

            call.respondHtml {
                head {
                    title {
                        +"ToDo List"
                    }
                }

                body {
                    h1 { +"ToDo List" }
                    for (toDo in response.toDos) {
                        div {
                            p { +toDo.content }
                            p { +toDo.createdAt.toString() }
                        }
                        hr {  }
                    }
                }
            }
        }
    }
}