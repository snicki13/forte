package de.snickit.forte.persistence

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Duration
import java.time.LocalDateTime

object WorkingSessions: IntIdTable() {
    val task = reference("task", Tasks)
    val startingTime = datetime("startingTime").nullable()
    val endTime = datetime("endTime").nullable()
}

class WorkingSession(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkingSession>(WorkingSessions)
    var task by Task referencedOn WorkingSessions.task
    var startingTime by WorkingSessions.startingTime
    var endTime by WorkingSessions.endTime

    fun stopSession(): WorkingSession {
        this.endTime = LocalDateTime.now()
        return this
    }

    fun startSession(): WorkingSession {
        this.startingTime = LocalDateTime.now()
        return this
    }

    fun getElapsedTime(): Duration = startingTime?.let { Duration.between(startingTime, endTime ?: LocalDateTime.now()) }?: Duration.ZERO
}

object WorkingSessionQueries {

    fun selectCurrentOrLatestSession(task: Task): WorkingSession? {
        return transaction {
            return@transaction WorkingSession.wrapRows(WorkingSessions
                .slice(WorkingSessions.columns)
                .select { WorkingSessions.task eq task.id }
                .orderBy(WorkingSessions.id, SortOrder.DESC)).firstOrNull()
        }
    }

}
