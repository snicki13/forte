package de.snickit.forte.model

import de.snickit.forte.view.TaskViewElement
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.select

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

    var active: Boolean = false

    fun getTitle() = "$name ($category)"
}

object TaskQueries {

}