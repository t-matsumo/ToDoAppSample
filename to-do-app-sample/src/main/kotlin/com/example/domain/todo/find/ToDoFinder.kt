package com.example.domain.todo.find

import com.example.domain.todo.AuthorId
import com.example.domain.todo.Todo

class ToDoFinder(private val findRecentToDoQuery: FindRecentToDoQuery) {
    fun findRecentToDo(id: AuthorId, maxCount: Int): List<Todo> {
        return findRecentToDoQuery.find(id, maxCount)
            .filter { it.authorId == id }
            .take(maxCount)
    }
}