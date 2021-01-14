package de.snickit.forte.persistence

import de.snickit.forte.Utility.getElapsedTimeString
import java.time.LocalDate

data class TaskResult(val task: Task, val date: LocalDate) {

    val name = task.task
    val project = task.project
    val duration = task.getFullDurationPerDay(date).getElapsedTimeString()

}
