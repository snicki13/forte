package de.snickit.forte.view

import tornadofx.View
import tornadofx.borderpane

class ForteMainView : View() {

    override val root = borderpane {
        top(Header::class)
        center(TaskView::class)
        bottom(Footer::class)
    }
}