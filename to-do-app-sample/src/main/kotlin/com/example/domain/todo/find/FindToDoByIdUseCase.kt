package com.example.domain.todo.find

import com.example.domain.todo.find.response.ToDo

data class FindToDoByIdRequest(val id: String)
data class FindToDoByIdResponse(val toDo: Result<ToDo>)

class FindToDoByIdNoSuchElementException: NoSuchElementException()

interface FindToDoByIdUseCase {
    fun handle(request: FindToDoByIdRequest): FindToDoByIdResponse
}