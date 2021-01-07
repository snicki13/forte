package de.snickit.forte.view.main

import de.snickit.forte.controller.ForteController
import de.snickit.forte.view.Styles
import tornadofx.*

class Header : View() {
    override val root = vbox {
        addClass(Styles.header)
        label("Forte - Timekeeper").setId(Styles.title)
    }
}
