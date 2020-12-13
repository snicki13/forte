package de.snickit.forte.model

import javafx.beans.property.StringProperty
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNull
import org.jetbrains.exposed.sql.`java-time`.datetime
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object WorkingSessions: IntIdTable() {
    val task = reference("task", Tasks)
    val startingTime = datetime("startingTime").nullable()
    val endTime = datetime("endTime").nullable()
}

class WorkingSession(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkingSession>(WorkingSessions)
    var task by WorkingSessions.task
    var startingTime by WorkingSessions.startingTime
    var endTime by WorkingSessions.endTime

    fun getElapsedTimeInSeconds(): Long = startingTime?.until(endTime ?: LocalDateTime.now(), ChronoUnit.SECONDS) ?: 0

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