package de.snickit.forte.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime
import java.time.Instant
import java.time.temporal.ChronoUnit

object WorkingSessions: IntIdTable() {
    val task = reference("task", Tasks)
    val startingTime = datetime("startingTime")
    val endTime = datetime("endTime").nullable()
}

class WorkingSession(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<WorkingSession>(WorkingSessions)
    var task by WorkingSessions.task
    var startingTime by WorkingSessions.startingTime
    var endTime by WorkingSessions.endTime

    fun getElapsedTimeInMinutes(): Long = startingTime.until(endTime ?: Instant.now(), ChronoUnit.MINUTES)
}