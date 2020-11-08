package de.snickit.forte.view

import de.snickit.forte.Task
import javafx.collections.ObservableList
import javafx.geometry.HPos
import javafx.geometry.Orientation
import javafx.scene.control.Button
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import tornadofx.*
import tornadofx.Stylesheet.Companion.button
import kotlin.time.ExperimentalTime

@ExperimentalTime
class ForteView : View("Register Customer") {

    private val taskPane = flowpane {
        orientation = Orientation.HORIZONTAL
        add(getAddButton())
    }

    override val root = taskPane

    private val model: ObservableList<Task> = observableListOf(Task("Eins"), Task("Zwei")).onChange {
        it.next()
        it.addedSubList.forEach { task ->
            addTask(task)
        }
        it.removed.forEach { task ->
            removeTask(task)
        }
    }

    private fun removeTask(task: Task) {
    }

    private fun addTask(task: Task) {
        with(taskPane) { pane {
                getAddButton()
            }
        }
    }

    private fun createTask() : Task = Task("Dummy")


    private fun getAddButton(): Button {
        return button {
            graphic = ImageView("outline_add_circle_outline_black_48dp.png")
            action {
                model.add(createTask())
            }
        }
    }
}
