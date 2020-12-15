package de.snickit.forte.model

import de.snickit.forte.Utility.getElapsedTimeString
import tornadofx.observable
import java.time.LocalDate

data class TaskResult(val task: Task, val date: LocalDate) {

    val name = task.name
    val category = task.category
    val duration = task.getFullDurationPerDay(date).getElapsedTimeString()

}
