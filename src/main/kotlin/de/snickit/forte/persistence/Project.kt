package de.snickit.forte.persistence

import de.snickit.forte.Utility.sumDuration
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import java.time.Duration
import java.time.LocalDate

object Projects: IntIdTable() {

    val project = varchar("project_name", 50).uniqueIndex()
    val color = varchar("color", length = 10)
}

class Project(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Project>(Projects)
    var project by Projects.project
    var color by Projects.color

    private val tasks by Task referrersOn Tasks.project

    fun getFullDurationPerDay(date: LocalDate): Duration = tasks.sumDuration { it.getFullDurationPerDay(LocalDate.now()) }

    fun toProjectDTO(): ProjectDTO {
        return ProjectDTO(id.value, project, color);
    }
}

data class ProjectDTO(
    val id: Int,
    val project: String,
    val color: String
)
