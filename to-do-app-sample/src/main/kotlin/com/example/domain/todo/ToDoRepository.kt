package com.example.domain.todo

interface ToDoRepository {
    fun nextId(): ToDoId
    fun save(toDo: Todo)
    fun findRecent(count: Int): List<Todo>
}