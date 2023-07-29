package com.example.infrastructure

import com.example.domain.todo.*
import java.util.*
import kotlin.NoSuchElementException

val inMemoryToDoDataStore = hashMapOf<ToDoId, Todo>()

class ToDoRepositoryInMemory: ToDoRepository {
    override fun nextId() = ToDoId(UUID.randomUUID().toString())
    override fun idFromString(string: String) = ToDoId(string)

    override fun save(toDo: Todo) {
        inMemoryToDoDataStore[toDo.id] = toDo
    }

    override fun find(id: ToDoId): Result<Todo> {
        return inMemoryToDoDataStore[id].let { if (it != null) Result.success(it) else Result.failure(NoSuchElementException()) }
    }

    override fun delete(toDo: Todo) {
        inMemoryToDoDataStore.remove(toDo.id)
    }
}