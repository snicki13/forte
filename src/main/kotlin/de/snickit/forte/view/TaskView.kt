package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Task
import javafx.collections.ObservableList
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import tornadofx.*
import kotlin.time.ExperimentalTime

class TaskView : View("Register Customer") {

    private val controller: ForteController by inject()

    private val taskPane = flowpane {
        orientation = Orientation.HORIZONTAL
        add(getAddButton())
    }

    override val root = taskPane

    private fun removeTask(task: Task) {
    }

    private fun addTask(task: Task) {
        with(taskPane) { pane {
                getAddButton()
            }
        }
    }



    private fun getAddButton(): Button {
        return button {
            graphic = ImageView("outline_add_circle_outline_black_48dp.png")
        }
    }
}
