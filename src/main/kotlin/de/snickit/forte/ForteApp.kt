package de.snickit.forte

import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSessions
import de.snickit.forte.server.HttpServer
import de.snickit.forte.view.Styles
import de.snickit.forte.view.main.ForteMainView
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.stage.Stage
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.App
import tornadofx.launch
import java.awt.*
import java.io.IOException
import java.net.URI
import javax.imageio.ImageIO
import javax.swing.SwingUtilities
import kotlin.system.exitProcess

class ForteApp: App(ForteMainView::class, Styles::class) {

    private lateinit var stage: Stage

    override fun start(stage: Stage) {
        SwingUtilities.invokeLater {
            addAppToTray()
        }
        Platform.setImplicitExit(false)

        this.stage = stage
        stage.onCloseRequest = EventHandler { stage.hide() }
        stage.width = 570.0
        stage.height = 400.0
        super.start(stage)
    }

    /**
     * Sets up a system tray icon for the application.
     */
    private fun addAppToTray() {
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
        trayIcon.addActionListener {
            Platform.runLater(
                ::showStage
            )
        }

        val openDesktopApp = MenuItem("Open Desktop App")
        openDesktopApp.addActionListener {
            Platform.runLater (
                ::showStage
            )
        }

        // Create a pop-up menu components
        val openBrowser = MenuItem("Open Browser")
        openBrowser.addActionListener {
            openBrowser()
        }

        val stopServer = MenuItem("Stop Server")
        stopServer.addActionListener {
            HttpServer.stop()
        }

        val exitItem = MenuItem("Exit")
        exitItem.addActionListener {
            HttpServer.stop()
            Platform.exit()
            tray.remove(trayIcon)
            exitProcess(0)
        }

        //Add components to pop-up menu
        popup.add(openDesktopApp)
        popup.addSeparator()
        popup.add(openBrowser)
        popup.add(stopServer)
        popup.addSeparator()
        popup.add(exitItem)

        trayIcon.popupMenu = popup

        tray.add(trayIcon)
    }

    private fun showStage() {
        stage.show()
        stage.toFront()
    }

    private fun openBrowser() {
        HttpServer.start()
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(URI("http://localhost:${Utility.getProperty("http.port")}"))
        }
    }

    companion object {
        @Throws(IOException::class, AWTException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            Database.connect(Utility.getProperty("jdbcUrl"), Utility.getProperty("jdbcDriver"), Utility.getProperty("jdbcUsername"), Utility.getProperty("jdbcPassword"))
            transaction {
                SchemaUtils.createMissingTablesAndColumns(Tasks, WorkingSessions)
            }
            launch<ForteApp>(*args)
        }

    }
}
