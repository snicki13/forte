package de.snickit.forte.controller

import de.snickit.forte.model.Task
import de.snickit.forte.model.TaskQueries
import de.snickit.forte.model.Tasks
import de.snickit.forte.model.WorkingSession
import org.jetbrains.exposed.sql.transactions.transaction
import tornadofx.Controller
import tornadofx.SortedFilteredList
import java.time.Instant
import java.time.LocalDateTime

class ForteController : Controller() {

    private val tasks = SortedFilteredList<Task>()
    private var activeWorkingSession: WorkingSession? = null

    init {
        transaction {
            tasks.addAll(Task.all())
        }
    }

    fun startWorkingSession(task: Task) {
        activeWorkingSession?.endTime = LocalDateTime.now()
        activeWorkingSession = WorkingSession.new {
            this.task = task.id
            this.startingTime = LocalDateTime.now()
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
}

