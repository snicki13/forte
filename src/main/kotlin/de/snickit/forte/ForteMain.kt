package de.snickit.forte
import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import de.snickit.forte.server.HttpServer
import de.snickit.forte.view.Styles
import de.snickit.forte.view.main.ForteMainView
import javafx.event.EventHandler
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import tornadofx.App
import java.awt.*
import java.net.URI
import java.util.*
import javax.imageio.ImageIO
import kotlin.system.exitProcess


object ForteMain {

    val prop = Properties()

    @JvmStatic
    fun main(args: Array<String>) {
        ClassLoader.getSystemResourceAsStream("database.properties").use { prop.load(it) }

        Database.connect(prop.getProperty("jdbcUrl"), prop.getProperty("jdbcDriver"), prop.getProperty("jdbcUsername"), prop.getProperty("jdbcPassword"))
        transaction {
            SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)
        }

        // GlobalScope.launch {
        //    Application.launch(ForteApp::class.java, *args)
        //}
        GlobalScope.launch {
            HttpServer.start(prop.getProperty("http.port").toInt())
        }
        setTrayIcon()
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

    fun setTrayIcon() {
        //Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            println("SystemTray is not supported")
            return
        }
        val popup = PopupMenu()
        val image: Image = ImageIO.read(ClassLoader.getSystemResourceAsStream("time-xxl.png"))
        val trayIcon = TrayIcon(image)
        trayIcon.isImageAutoSize = true
        val tray = SystemTray.getSystemTray()

        // Create a pop-up menu components
        val openBrowser = MenuItem("Open Browser")
        openBrowser.addActionListener {
            openBrowser()
        }
        val exitItem = MenuItem("Exit")
        exitItem.addActionListener {
            exitProcess(0)
        }

        //Add components to pop-up menu
        popup.add(openBrowser)
        popup.add(exitItem)

        trayIcon.popupMenu = popup

        try {
            tray.add(trayIcon)
        } catch (e: AWTException) {
            println("TrayIcon could not be added.")
        }
    }

    fun openBrowser() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI("http://localhost:${prop.getProperty("http.port")}"));
        }
    }
}

