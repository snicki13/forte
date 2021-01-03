package de.snickit.forte.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration
import java.time.LocalDate

object Tasks: IntIdTable() {

    val name = varchar("name", 50)
    val category = varchar("category", 50)
    val color = varchar("color", length = 10)
}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var name by Tasks.name
    var category by Tasks.category
    var color by Tasks.color

    private val workingSessions by WorkingSession referrersOn WorkingSessions.task

    var active: Boolean = false

    fun getTitle() = "$name ($category)"

    fun getFullDurationPerDay(date: LocalDate): Duration {
        return transaction {
            return@transaction workingSessions.filter { it.startingTime != null }
                .filter { date.isEqual(it.startingTime?.toLocalDate()) }
                .sumDuration { it.getElapsedTime() }
        }
    }

    private inline fun <T> Iterable<T>.sumDuration(selector: (T) -> Duration): Duration {
        var sum = Duration.ZERO
        for (element in this) {
            sum = sum.plus(selector(element))
        }
        return sum
    }

    public fun toTaskDTO(): TaskDTO {
        return TaskDTO(id.value, name, category, color);
    }
}

data class TaskDTO(
    val id: Int,
    val name: String,
    val category: String,
    val color: String
)

