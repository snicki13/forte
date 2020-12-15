package de.snickit.forte
import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import de.snickit.forte.view.Styles
import de.snickit.forte.view.main.ForteMainView
import javafx.application.Application
import javafx.event.EventHandler
import javafx.stage.Stage
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import tornadofx.App
import java.util.*

object ForteMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val prop = Properties()
        ClassLoader.getSystemResourceAsStream("database.properties").use { prop.load(it) }

        Database.connect(prop.getProperty("jdbcUrl"), prop.getProperty("jdbcDriver"), prop.getProperty("jdbcUsername"), prop.getProperty("jdbcPassword"))
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)
        }

        Application.launch(ForteApp::class.java, *args)
    }

    class ForteApp : App(ForteMainView::class, Styles::class) {

        private val forteController: ForteController by inject()
        private val logger = LoggerFactory.getLogger(this.javaClass)

        override fun start(stage: Stage) {
            super.start(stage)
            stage.width = 570.0
            stage.height = 400.0

            stage.onCloseRequest = EventHandler {
                forteController.stopActiveSession()
                logger.info("Close request")
            }
        }
    }
}

