package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import javafx.event.EventHandler
import javafx.stage.Stage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tornadofx.*

class ForteApp : App(ForteMainView::class, Styles::class) {

    private val forteController: ForteController by inject()
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 570.0
        stage.height = 400.0

        stage.onCloseRequest = EventHandler {
            logger.info("Close request")
        }
    }


}