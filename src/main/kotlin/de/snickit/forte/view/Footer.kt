package de.snickit.forte.view

import de.snickit.forte.view.Styles
import de.snickit.forte.controller.ForteController
import tornadofx.*

class Footer : View() {
    private val controller: ForteController by inject()
    private val itemsActive = integerBinding(controller.getTasks()) { count() }

    override val root = hbox {
        addClass(Styles.footer)
        label(stringBinding(itemsActive) { "$value item${value.plural} left" })
    }

    private val Int.plural: String get() = if (this == 1) "" else "s"
}