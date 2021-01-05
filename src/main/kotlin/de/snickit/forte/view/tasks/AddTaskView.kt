package de.snickit.forte.view.tasks

import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Task
import de.snickit.forte.view.Styles
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.*
import tornadofx.*

import javafx.scene.layout.GridPane


object AddTaskView: View() {

    private val forteController by inject<ForteController>()

    override val root = borderpane {
        this.addClass(Styles.taskViewElement)

        style {
            borderRadius = multi(box(10.px))
            backgroundRadius = multi(box(10.px))
        }
        center = button {
            graphic = Styles.addIcon()
            addClass(Styles.addButton)
            onAction = EventHandler {
                getModalDialog().showAndWait()
            }
        }
    }

    private fun getModalDialog(): Dialog<Task> {
        // Create the custom dialog.
        val dialog: Dialog<Task> = Dialog()
        dialog.title = "New Task"
        dialog.headerText = "Configure new Task"

        // Set the button types.
        dialog.dialogPane.buttonTypes.addAll(ButtonType.OK, ButtonType.CANCEL)

        // Create the username and password labels and fields.
        val grid = GridPane()
        grid.hgap = 10.0
        grid.vgap = 10.0
        grid.padding = Insets(20.0, 150.0, 10.0, 10.0)

        val task = TextField()
        task.promptText = "Task"
        val category = TextField()
        category.promptText = "Category"
        val colorPicker = ColorPicker()

        grid.add(Label("Task:"), 0, 0)
        grid.add(task, 1, 0)
        grid.add(Label("Category:"), 0, 1)
        grid.add(category, 1, 1)
        grid.add(Label("Farbe:"), 0, 2)
        grid.add(colorPicker, 1, 2)


        // Enable/Disable login button depending on whether a username was entered.
        val okButton = dialog.dialogPane.lookupButton(ButtonType.OK)
        okButton.isDisable = true

        // Do some validation (using the Java 8 lambda syntax).
        task.textProperty()
            .addListener { _, _, newValue -> okButton.isDisable = newValue.trim().isEmpty() }

        dialog.dialogPane.content = grid

        // Request focus on the username field by default.
        Platform.runLater { task.requestFocus() }


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter { dialogButton ->
            if (dialogButton === ButtonType.OK) {

                return@setResultConverter forteController.addTask(task.text, category.text, colorPicker.value.toString())
            }
            null
        }
        return dialog
    }
}
