package com.example.domain.todo

interface ToDoRepository {
    fun nextId(): Id
    fun idFromString(string: String): Id

    fun save(toDo: Todo)
    fun find(id: Id): Todo?
    fun delete(toDo: Todo)
}