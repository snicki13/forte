package de.snickit.forte.view.tasks

import de.snickit.forte.controller.ForteController
import javafx.geometry.Insets
import tornadofx.*

class TaskView : View("My View") {
    private val forteController by di<ForteController>()

    override val root = flowpane {

        AddTaskView.add(this)

        this.padding = Insets(10.0)
        this.vgap = 10.0
        this.hgap = 10.0

        forteController.getTasks().onChange {
            if (it.next()) {
                it.addedSubList.forEach { task ->
                    this.children.remove(children.last())
                    this.add(TaskViewElement(task))
                    this.add(AddTaskView)
                }
            }
        }

        forteController.getTasks().forEach {
            this.add(TaskViewElement(it))
        }

        this.add(AddTaskView)

    }
}
