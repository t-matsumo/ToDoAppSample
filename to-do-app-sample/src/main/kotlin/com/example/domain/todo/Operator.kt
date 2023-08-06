package com.example.domain.todo

@JvmInline
value class OperatorId(val value: String)

data class Operator(val id: OperatorId) {
    fun canRead(todo: Todo): Boolean {
        return this.id == todo.operatorId
    }

    fun canEdit(todo: Todo): Boolean {
        return this.id == todo.operatorId
    }
}