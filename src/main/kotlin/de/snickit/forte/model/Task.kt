package de.snickit.forte.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select

object Tasks: IntIdTable() {

    val name = varchar("name", 50).uniqueIndex()
    val category = varchar("category", 50)
    val color = varchar("color", length = 6)

}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var name by Tasks.name
    var category by Tasks.category
    var color by Tasks.color

    fun getTitle() = "$name ($category)"
}

object TaskQueries {

    private val selectActiveTaskQuery = Tasks
        .innerJoin(WorkingSessions)
        .slice(Tasks.columns)
        .select { Tasks.id eq WorkingSessions.task }.andWhere { WorkingSessions.endTime.isNull() }

    fun selectActiveTask() = Task.wrapRows(selectActiveTaskQuery).toList()


}