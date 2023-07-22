package com.example.domain.todo.find

import com.example.domain.todo.ToDoRepository
import com.example.domain.todo.find.response.ToDo

class FindToDoByIdInteractor(private val repository: ToDoRepository): FindToDoByIdUseCase {
    override fun handle(request: FindToDoByIdRequest): FindToDoByIdResponse {
        return repository
            .find(repository.idFromString(request.id))
            .fold(
                onSuccess = {
                    val response = ToDo(
                        it.idString,
                        it.titleString,
                        it.contentString,
                        it.createdAtLocalDateTime
                    )
                    Result.success(response)
                            },
                onFailure = { Result.failure(FindToDoByIdNoSuchElementException()) }
            )
            .let { FindToDoByIdResponse(it) }
    }
}