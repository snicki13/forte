package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import tornadofx.*

class TaskView : View("My View") {
    private val controller: ForteController by inject()

    override val root = flowpane {
        controller.getTasks().forEach {
            this.add(TaskViewElement(it))
        }

    }
}
