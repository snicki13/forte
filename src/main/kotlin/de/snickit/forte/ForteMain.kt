package de.snickit.forte
import de.snickit.forte.model.Task
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import javafx.application.Application
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import kotlin.time.ExperimentalTime

@ExperimentalTime
object ForteMain {

    @JvmStatic
    fun main(args: Array<String>) {
        Database.connect("jdbc:sqlite:forte.db")

        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)

            Task.new {
                name = "Test"
                category = "Test"
            }
        }


        //Application.launch(ForteApp::class.java, *args)
    }
}


