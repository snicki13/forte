package de.snickit.forte.view.main

import de.snickit.forte.view.Styles
import de.snickit.forte.controller.ForteController
import de.snickit.forte.view.results.DailyResultView
import javafx.event.EventHandler
import javafx.scene.layout.Priority
import tornadofx.*
import java.time.LocalDate

class Footer : View() {
    private val controller: ForteController by inject()
    private val itemsActive = integerBinding(controller.getTasks()) { count() }

    override val root = hbox {
        addClass(Styles.footer)
        add(label(stringBinding(itemsActive) { "$value item${value.plural} left" }))
        add(region {hgrow = Priority.ALWAYS})
        add(button("Daily Result") {
            onAction = EventHandler {
                DailyResultView(controller.getTasks(), LocalDate.now()).show()
            }
        })
    }

    private val Int.plural: String get() = if (this == 1) "" else "s"
}