package de.snickit.forte.controller

import de.snickit.forte.model.*
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller
import tornadofx.SortedFilteredList
import tornadofx.onChange
import java.time.LocalDateTime

class ForteController : Controller() {

    private val tasks = SortedFilteredList<Task>()

    init {
        transaction {
            tasks.addAll(Task.all())
        }
        tasks.onChange {

        }
    }

    fun getTasks() = tasks

    fun addTask(name: String, category: String, color: String): Task {
        return transaction {
            val taskIterator = Task.find { (Tasks.name eq name).and(Tasks.category eq category)}
            return@transaction if (taskIterator.count() == 0L) {
                val task = Task.new {
                    this.name = name
                    this.category = category
                    this.color = color
                }
                tasks.add(task)
                task
            } else {
                taskIterator.first()
            }
        }
    }

    fun getLatestOrNewWorkingSession(task: Task): WorkingSession {
        val latestSession = WorkingSessionQueries.selectCurrentOrLatestSession(task)
        return transaction {
            if (latestSession == null || latestSession.endTime != null) {
                WorkingSession.new {
                    this.task = task.id
                }
            } else {
                latestSession
            }
        }
    }

    fun startWorkingSession(workingSession: WorkingSession): WorkingSession {
        transaction {
            workingSession.startingTime = LocalDateTime.now()
        }
        return workingSession
    }

    fun stopWorkingSession(workingSession: WorkingSession): WorkingSession {
        return transaction {
            workingSession.endTime = LocalDateTime.now()
            return@transaction WorkingSession.new {
                this.task = workingSession.task
            }
        }
    }
}
