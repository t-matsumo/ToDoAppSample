package com.example.domain.todo.find

import com.example.domain.todo.AuthorId
import com.example.domain.todo.Todo

interface FindRecentToDoQuery {
    fun find(id: AuthorId, maxCount: Int): List<Todo>
}