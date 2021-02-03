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

    val project = reference("project", Projects.id)
    val task = varchar("name", length = 50)
    val color = varchar("color", length = 10)
}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var task by Tasks.task
    private var projectFk by Project referencedOn Tasks.project
    var project: Project
        get() = transaction { return@transaction this@Task.projectFk }
        set(value) = transaction { this@Task.projectFk = value }

    var color by Tasks.color

    private val workingSessions by WorkingSession referrersOn WorkingSessions.task

    var active: Boolean = false

    fun getTitle() = "$task (${project.project})"

    fun getFullDurationPerDay(date: LocalDate): Duration {
        return transaction {
            return@transaction workingSessions.filter { it.startingTime != null }
                .filter { date.isEqual(it.date) }
                .sumDuration { it.getElapsedTime() }
        }
    }

    fun getDatesWithWork(): Set<LocalDate?> = workingSessions.map { it.date }.toHashSet()

    fun toTaskDTO(): TaskDTO {
        return TaskDTO(this@Task.id.value, task, project.project.value, color)
    }
}

data class TaskDTO(
    val id: Int,
    val name: String,
    val project: String,
    val color: String
)

