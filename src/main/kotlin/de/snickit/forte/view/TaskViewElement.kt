package de.snickit.forte.view

import de.snickit.forte.model.Task
import javafx.geometry.Pos
import javafx.scene.layout.GridPane
import tornadofx.*

class TaskViewElement(private val task: Task) : View("TaskViewElement") {
    override val root = titledpane {
        text = task.getTitle()
        borderpane {
            style {
                backgroundColor += c(task.color)
            }
            center = getCenterGridPane()
            bottom = togglebutton {
                text = "Start"
                this.apply {
                    alignment = Pos.CENTER
                }
            }
        }
    }

    private fun getCenterGridPane(): GridPane {
        return gridpane {
            row {
                add(label { text = "Last" })
                add(textfield { isEditable = false })
            }
            row {
                add(label { text = "Total" })
                add(textfield { isEditable = false })
            }
        }
    }
}
