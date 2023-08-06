package com.example.domain.todo.find

import com.example.domain.todo.find.response.ToDo

data class FindToDoByIdRequest(val operatorId: String, val id: String)
data class FindToDoByIdResponse(val toDo: ToDo?)

interface FindToDoByIdUseCase {
    fun handle(request: FindToDoByIdRequest): FindToDoByIdResponse
}