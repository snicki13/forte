package de.snickit.forte.view.results

import com.sun.javafx.scene.control.skin.DatePickerSkin
import de.snickit.forte.controller.ForteController
import de.snickit.forte.persistence.Project
import javafx.beans.binding.Bindings
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.paint.Color
import javafx.stage.Stage
import tornadofx.*
import java.time.LocalDate

import javafx.util.Callback
import java.util.concurrent.Callable

class CalendarView(private val forteController: ForteController): Stage() {

    init {
        this.title = "Calendar Result "
        val project = ComboBox<Project>()
        project.items = forteController.getProjects()
        project.converter = Project.ProjectConverter()
        project.value = forteController.activeProject
        var calendar: Node = buildCalendar(project.value)
        project.valueProperty().onChange {
            calendar = buildCalendar(it)
        }

        this.scene = Scene(
            borderpane {
                top = project
                val binding = Bindings.createObjectBinding(calendarCallable(project.value), centerProperty())
                center = binding.value
            }
        )
    }

    private fun calendarCallable(project: Project?): Callable<Node> = Callable {
            buildCalendar(project)
    }

    private fun buildCalendar(project: Project?): Node {
        val datePicker = DatePicker(LocalDate.now())

        if (project != null) {
            datePicker.dayCellFactory = dayCellFactory(project.getDaysPerMonthWithWork())
        }
        val calender = DatePickerSkin(datePicker).popupContent
        calender.onDoubleClick {
            DailyResultView(forteController.getTasks(), datePicker.value).show()
        }
        return calender
    }

    private fun dayCellFactory(dates: HashSet<LocalDate?>): Callback<DatePicker?, DateCell?> = Callback {
        object : DateCell() {
            override fun updateItem(item: LocalDate, empty: Boolean) {
                super.updateItem(item, empty)
                if (dates.contains(item)) {
                    style {
                        accentColor = Color.GREEN
                        baseColor = Color.GREEN
                    }
                }
            }
        }
    }
}
