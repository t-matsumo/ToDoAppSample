package com.example.domain.todo.find.response

import java.time.LocalDateTime

data class ToDo(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
)