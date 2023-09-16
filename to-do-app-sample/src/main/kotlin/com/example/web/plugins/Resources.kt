package com.example.web.plugins

import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.Resources

fun Application.configureResources() {
    install(Resources)
}

@Resource("/todos")
class ToDoForWeb {
    @Resource("{id}")
    class Id(val parent: ToDoForWeb, val id: String) {
        @Resource("edit")
        class Edit(val parent: Id)

        @Resource("delete")
        class Delete(val parent: Id)
    }
}