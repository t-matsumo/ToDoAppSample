package com.example.infrastructure

import com.example.domain.todo.*
import java.time.LocalDateTime
import java.util.*


class ToDoRepositoryInMemory: ToDoRepository {
    private val dataStore = hashMapOf<ToDoId, Todo>()

    init {
        val ids = List(12) { nextId() }
        for ((i, id) in ids.withIndex()) {
            dataStore[id] = Todo(id, ToDoContent("content $i"), ToDoCreatedAt(LocalDateTime.now()))
        }
    }
    
    override fun nextId() = ToDoId(UUID.randomUUID())

    override fun save(toDo: Todo) {
        dataStore[toDo.id] = toDo
    }

    override fun findRecent(count: Int): List<Todo> {
        return dataStore
            .values
            .sortedByDescending { it.createdAt }
            .take(count)
    }
}