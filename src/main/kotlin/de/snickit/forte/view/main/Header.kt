package de.snickit.forte.view.main

import de.snickit.forte.controller.ForteController
import de.snickit.forte.view.Styles
import tornadofx.*

class Header : View() {
    private val forteController by inject<ForteController>()

    override val root = vbox {
        addClass(Styles.header)
        label("Forte - Timekeeper").setId(Styles.title)
    }
}
