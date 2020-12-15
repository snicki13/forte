package de.snickit.forte.view.main

import de.snickit.forte.view.tasks.TaskView
import tornadofx.View
import tornadofx.borderpane

class ForteMainView : View() {

    init {
        title = "Forte - Timekeeper"
    }

    override val root = borderpane {
        top(Header::class)
        center(TaskView::class)
        bottom(Footer::class)
    }
}