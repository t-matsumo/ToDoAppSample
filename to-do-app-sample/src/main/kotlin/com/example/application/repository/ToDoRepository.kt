package com.example.application.repository

import com.example.domain.todo.Id
import com.example.domain.todo.Todo

interface ToDoRepository {
    fun nextId(): Id
    fun idFromString(string: String): Id

    fun save(toDo: Todo)
    fun find(id: Id): Todo?
    fun delete(toDo: Todo)
}