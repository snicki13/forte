package de.snickit.forte.persistence

import de.snickit.forte.Utility.sumDuration
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration
import java.time.LocalDate

object Tasks: IntIdTable() {

    val project = reference("projectName", Projects.project)
    val task = varchar("name", length = 50)
    val color = varchar("color", length = 10)
}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var task by Tasks.task
    var project by Tasks.project
    var color by Tasks.color

    private val workingSessions by WorkingSession referrersOn WorkingSessions.task

    var active: Boolean = false

    fun getTitle() = "$task ($project)"

    fun getFullDurationPerDay(date: LocalDate): Duration {
        return transaction {
            return@transaction workingSessions.filter { it.startingTime != null }
                .filter { date.isEqual(it.startingTime?.toLocalDate()) }
                .sumDuration { it.getElapsedTime() }
        }
    }

    fun toTaskDTO(): TaskDTO {
        return TaskDTO(id.value, task, project, color);
    }
}

data class TaskDTO(
    val id: Int,
    val name: String,
    val category: String,
    val color: String
)

