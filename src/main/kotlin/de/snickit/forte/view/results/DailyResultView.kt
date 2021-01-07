package de.snickit.forte.view.results

import de.snickit.forte.persistence.Task
import de.snickit.forte.persistence.TaskResult
import javafx.collections.ObservableList
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import tornadofx.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DailyResultView(private val tasks: ObservableList<Task>, private val date: LocalDate): Dialog<Any>() {

    init {
        val taskResults = tasks.mapTo(SortedFilteredList()) { TaskResult(it, date) }
        this.title = "Result for ${date.format(DateTimeFormatter.ISO_DATE)}"
        this.dialogPane.buttonTypes.addAll(ButtonType.FINISH)
        this.dialogPane.content = tableview(taskResults) {
            readonlyColumn("Task", TaskResult::name)
            readonlyColumn("Category", TaskResult::category)
            readonlyColumn("Duration", TaskResult::duration)
        }
    }

}
