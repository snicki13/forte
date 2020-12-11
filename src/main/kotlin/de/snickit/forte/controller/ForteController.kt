package de.snickit.forte.controller

import de.snickit.forte.model.*
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller
import tornadofx.SortedFilteredList
import java.time.LocalDateTime

class ForteController : Controller() {

    private val tasks = SortedFilteredList<Task>()

    init {
        transaction {
            tasks.addAll(Task.all())
        }
    }

    fun getActiveTask() = transaction { TaskQueries.selectActiveTask().getOrNull(0) }

    fun getTasks() = tasks

    fun addTask(name: String): Task {
        return transaction {
            val taskIterator = Task.find { Tasks.name eq name }
            return@transaction if (taskIterator.count() == 0L) {
                val task = Task.new {
                    this.name = name
                    category = "test"
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

