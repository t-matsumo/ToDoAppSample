package com.example.domain.todo

interface ToDoRepository {
    fun nextId(): ToDoId
    fun idFromString(string: String): ToDoId

    fun save(toDo: Todo)
    fun find(id: ToDoId): Result<Todo>
    fun delete(toDo: Todo)
}