package com.example.application.usecase.task.find.response

import java.time.LocalDateTime

data class ToDo(
    val id: String,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
)