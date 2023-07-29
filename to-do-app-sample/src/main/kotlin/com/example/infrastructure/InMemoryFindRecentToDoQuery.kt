package com.example.infrastructure

import com.example.domain.todo.AuthorId
import com.example.domain.todo.Todo
import com.example.domain.todo.find.FindRecentToDoQuery

class InMemoryFindRecentToDoQuery: FindRecentToDoQuery {
    override fun find(id: AuthorId, maxCount: Int): List<Todo> {
        return inMemoryToDoDataStore
            .values
            .filter { it.authorId == id }
            .sortedByDescending { it.createdAt }
            .take(maxCount)
    }
}