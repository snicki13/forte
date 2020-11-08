package de.snickit.forte

import de.snickit.forte.view.ForteView
import javafx.stage.Stage
import tornadofx.*
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ForteApp : App(ForteView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 600.0
    }
}