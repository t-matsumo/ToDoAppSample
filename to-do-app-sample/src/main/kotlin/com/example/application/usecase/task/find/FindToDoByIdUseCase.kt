package com.example.application.usecase.task.find

import com.example.application.usecase.task.find.response.ToDo

data class FindToDoByIdRequest(val operatorId: String, val id: String)
data class FindToDoByIdResponse(val toDo: ToDo?)

interface FindToDoByIdUseCase {
    fun handle(request: FindToDoByIdRequest): FindToDoByIdResponse
}