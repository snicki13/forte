package de.snickit.forte.server

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

object HttpServer {

    fun start(port: Int) {
        embeddedServer(Netty, port = port) {
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
            }
        }.start(wait = true)

    }

}
