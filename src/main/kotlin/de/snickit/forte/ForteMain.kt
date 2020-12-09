package de.snickit.forte
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import de.snickit.forte.view.ForteApp
import javafx.application.Application
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.FileInputStream
import java.util.*

object ForteMain {

    @JvmStatic
    fun main(args: Array<String>) {
        val prop = Properties()
        ClassLoader.getSystemResourceAsStream("database.properties").use { prop.load(it) }

        Database.connect(prop.getProperty("jdbcUrl"))
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)
        }

        Application.launch(ForteApp::class.java, *args)
    }
}


