package de.snickit.forte.server

import de.snickit.forte.Utility
import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Task
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import tornadofx.Component
import java.util.concurrent.TimeUnit

object HttpServer: Component() {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    private val forteController: ForteController by inject()

    private var isRunning: Boolean = false

    private val applicationServer = embeddedServer(Netty, port = Utility.getProperty("http.port").toInt()) {
        install(ContentNegotiation) {
            jackson()
        }
        install(CallLogging) {
            logger = LoggerFactory.getLogger("HTTP Request")
            level = Level.INFO
        }
        routing {
            staticContent()
            getTasks()
            get("/test") {
                call.respondText("Hello World")
            }
        }
   }

    private fun Routing.getTasks() {
        get("/tasks") {
            call.respond(forteController.getTasks().map { it.toTaskDTO() })
        }
        get("/task/{id}/latestSession") {
            val id: Int = call.parameters["id"]!!.toInt()
            call.respond(forteController.getLatestOrNewWorkingSession(Task.findById(id)!!))
        }
    }

    private fun Routing.staticContent() {
        static {
            resource("/", "/public/index.html")
            resource("*", "/public/index.html")
            static("/") {
                resources("public")
            }
        }
    }

    fun start() {
        if (!isRunning) {
            logger.info("Starting Server!")
            isRunning = true
            GlobalScope.launch {
                applicationServer.start(wait = true)
            }
        }
    }

    fun stop() {
        if (isRunning) {
            logger.info("Stopping Server!")
            applicationServer.stop(1, 2, TimeUnit.SECONDS)
            isRunning = false
        }
    }

}
