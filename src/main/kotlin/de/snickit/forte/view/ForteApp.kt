package de.snickit.forte.view

import javafx.stage.Stage
import tornadofx.*

class ForteApp : App(ForteMainView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.width = 570.0
        stage.height = 400.0
    }
}