package de.snickit.forte
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import de.snickit.forte.view.ForteApp
import javafx.application.Application
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object ForteMain {

    @JvmStatic
    fun main(args: Array<String>) {
        Database.connect("jdbc:sqlite:forte.db")
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)
        }

        Application.launch(ForteApp::class.java, *args)
    }
}


