package de.snickit.forte.server

import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Task
import de.snickit.forte.model.WorkingSession
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

object HttpServer {

    fun start(port: Int) {
        embeddedServer(Netty, port = port) {
            install(ContentNegotiation) {
                jackson()
            }
            routing {
                static {
                    resource("/", "/public/index.html")
                    resource("*", "/public/index.html")
                    static("/") {
                        resources("public")
                    }
                }
                get("/test") {
                    call.respondText("Hello World")
                }
                get("/tasks") {
                    call.respond(ForteController.getTasks().map { it.toTaskDTO() })
                }
                get("/task/{id}/latestSession") {
                    val id: Int = call.parameters["id"]!!.toInt()
                    call.respond(ForteController.getLatestOrNewWorkingSession(Task.findById(id)!!))
                }
            }
        }.start(wait = true)

    }

}
