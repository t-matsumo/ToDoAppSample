package com.example.domain.currentuser

@JvmInline
value class Id(val value: String)

class CurrentUser(val id: Id)