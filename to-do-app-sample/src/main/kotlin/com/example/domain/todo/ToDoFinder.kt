package com.example.domain.todo

class ToDoFinder(private val findRecentToDoQuery: FindRecentToDoQuery) {
    fun findRecentToDoBy(operator: Operator, maxCount: Int): List<Todo> {
        return findRecentToDoQuery.find(operator.id, maxCount)
            .filter { operator.canRead(it) }
            .take(maxCount)
    }
}