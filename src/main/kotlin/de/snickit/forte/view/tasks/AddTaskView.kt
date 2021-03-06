package de.snickit.forte.view.tasks

import de.snickit.forte.Utility.toHexString
import de.snickit.forte.controller.ForteController
import de.snickit.forte.persistence.Project
import de.snickit.forte.persistence.Task
import de.snickit.forte.view.Styles
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.control.*
import tornadofx.*

import javafx.scene.layout.GridPane

object AddTaskView: View() {

    private val forteController by di<ForteController>()

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
        val project = ComboBox<Project>()
        project.converter = Project.ProjectConverter()
        project.isEditable = true
        project.items = forteController.getProjects()
        project.value = forteController.activeProject

        val colorPicker = ColorPicker()

        grid.add(Label("Task:"), 0, 0)
        grid.add(task, 1, 0)
        grid.add(Label("Project:"), 0, 1)
        grid.add(project, 1, 1)
        grid.add(Label("Farbe:"), 0, 2)
        grid.add(colorPicker, 1, 2)

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

                return@setResultConverter forteController.addTask(task.text, project.value, colorPicker.value.toHexString())
            }
            null
        }
        return dialog
    }
}
