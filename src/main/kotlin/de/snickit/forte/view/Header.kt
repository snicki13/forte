package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import javafx.beans.property.StringProperty
import tornadofx.*

class Header : View() {
    private val controller: ForteController by inject()

    override val root = vbox {
        addClass(Styles.header)
        label("Forte - Timekeeper").setId(Styles.title)
    }
}