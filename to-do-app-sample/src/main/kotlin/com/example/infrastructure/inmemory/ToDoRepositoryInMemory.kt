package com.example.infrastructure.inmemory

import com.example.application.repository.ToDoRepository
import com.example.domain.todo.*
import java.util.*

val inMemoryToDoDataStore = hashMapOf<Id, Todo>()

class ToDoRepositoryInMemory: ToDoRepository {
    override fun nextId() = Id(UUID.randomUUID().toString())
    override fun idFromString(string: String) = Id(string)

    override fun save(toDo: Todo) {
        inMemoryToDoDataStore[toDo.id] = toDo
    }

    override fun find(id: Id): Todo? {
        return inMemoryToDoDataStore[id]
    }

    override fun delete(toDo: Todo) {
        inMemoryToDoDataStore.remove(toDo.id)
    }
}