package com.example.infrastructure

import com.example.domain.todo.*
import java.util.*
import kotlin.NoSuchElementException


class ToDoRepositoryInMemory: ToDoRepository {
    private val dataStore = hashMapOf<ToDoId, Todo>()

    init {
        val ids = List(12) { nextId() }
        for ((i, id) in ids.withIndex()) {
            dataStore[id] = Todo(id, ToDoContent("content $i"), ToDoCreatedAt.now())
        }
    }

    override fun nextId() = ToDoId(UUID.randomUUID().toString())
    override fun idFromString(string: String) = ToDoId(string)

    override fun save(toDo: Todo) {
        dataStore[toDo.id] = toDo
    }

    override fun findRecent(count: Int): List<Todo> {
        return dataStore
            .values
            .sortedByDescending { it.createdAt }
            .take(count)
    }

    override fun find(id: ToDoId): Result<Todo> {
        return dataStore[id].let { if (it != null) Result.success(it) else Result.failure(NoSuchElementException()) }
    }

    override fun delete(toDo: Todo) {
        dataStore.remove(toDo.id)
    }
}