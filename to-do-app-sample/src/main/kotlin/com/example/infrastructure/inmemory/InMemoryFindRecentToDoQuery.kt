package com.example.infrastructure.inmemory

import com.example.domain.todo.OperatorId
import com.example.domain.todo.Todo
import com.example.domain.todo.FindRecentToDoQuery

class InMemoryFindRecentToDoQuery: FindRecentToDoQuery {
    override fun find(id: OperatorId, maxCount: Int): List<Todo> {
        return inMemoryToDoDataStore
            .values
            .filter { it.operatorId == id }
            .sortedByDescending { it.createdAt }
            .take(maxCount)
    }
}