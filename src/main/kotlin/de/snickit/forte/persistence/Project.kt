package de.snickit.forte.persistence

import de.snickit.forte.Utility.sumDuration
import javafx.util.StringConverter
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration
import java.time.LocalDate

object Projects: IdTable<String>() {

    override val id = varchar("project_name", 50).entityId()
    val color = varchar("color", length = 10)

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

class Project(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Project>(Projects)
    var project by Projects.id
    var color by Projects.color

    private val tasks by Task referrersOn Tasks.project

    fun getFullDurationPerDay(date: LocalDate): Duration = tasks.sumDuration { it.getFullDurationPerDay(LocalDate.now()) }

    fun toProjectDTO(): ProjectDTO {
        return ProjectDTO(project.value, color);
    }

    override fun toString(): String {
        return project.value
    }

    class ProjectConverter: StringConverter<Project>() {
        override fun fromString(string: String): Project {
            return transaction {
                findById(string) ?: Project.new {
                    this.project = EntityID(string, Projects)
                    this.color = ""
                }
            }
        }
        override fun toString(project: Project?): String {
            return project?.project?.value ?: ""
        }
    }
}

data class ProjectDTO(
    val projectName: String,
    val color: String
)
