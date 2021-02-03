package de.snickit.forte.view.results

import com.sun.javafx.scene.control.skin.DatePickerSkin
import de.snickit.forte.controller.ForteController
import de.snickit.forte.persistence.Project
import javafx.scene.control.*
import javafx.scene.paint.Color
import tornadofx.*
import java.time.LocalDate

import javafx.util.Callback


class CalendarView(private val forteController: ForteController): Dialog<Any>() {


    init {
        this.title = "Result for "
        this.dialogPane.buttonTypes.addAll(ButtonType.FINISH)
        val project = ComboBox<Project>()
        project.items = forteController.getProjects()
        project.converter = Project.ProjectConverter()
        project.value = project.items[0]

        val datePicker = DatePicker(LocalDate.now())
        datePicker.dayCellFactory = dayCellFactory(project.value.getDaysPerMonthWithWork())

        val calender = DatePickerSkin(datePicker).popupContent
        calender.onDoubleClick {
            DailyResultView(forteController.getTasks(), LocalDate.now()).show()
        }
        this.dialogPane.content = pane {
            add(project)
            add(calender)
        }

    }

    private fun dayCellFactory(dates: HashSet<LocalDate?>): Callback<DatePicker?, DateCell?> = Callback {
        object : DateCell() {
            override fun updateItem(item: LocalDate, empty: Boolean) {
                super.updateItem(item, empty)
                if (dates.contains(item)) {
                    style {
                        backgroundColor = multi(Color.GREEN)
                    }
                }
            }
        }
    }
}
