package com.example.domain.todo.find

import com.example.domain.todo.OperatorId
import com.example.domain.todo.Todo

interface FindRecentToDoQuery {
    fun find(id: OperatorId, maxCount: Int): List<Todo>
}