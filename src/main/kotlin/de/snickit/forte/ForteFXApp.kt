package de.snickit.forte

import de.snickit.forte.controller.ForteController
import de.snickit.forte.view.Styles
import de.snickit.forte.view.main.ForteMainView
import javafx.event.EventHandler
import javafx.stage.Stage
import org.slf4j.LoggerFactory
import tornadofx.App

class ForteFXApp : App(ForteMainView::class, Styles::class) {

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
