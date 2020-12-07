package de.snickit.forte.model

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Tasks: IntIdTable() {

    val name = varchar("name", 50).uniqueIndex()
    val category = varchar("category", 50)

}

class Task(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Task>(Tasks)
    var name by Tasks.name
    var category by Tasks.category
}