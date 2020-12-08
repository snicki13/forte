package de.snickit.forte.view

import javafx.stage.Stage
import tornadofx.*

class ForteApp : App(ForteMainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 800.0
        stage.height = 600.0
    }
}