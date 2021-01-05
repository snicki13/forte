package de.snickit.forte.controller

import de.snickit.forte.model.*
import de.snickit.forte.view.tasks.TaskViewElement
import javafx.collections.ObservableList
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory
import tornadofx.Controller
import tornadofx.SortedFilteredList

class ForteController : Controller() {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    val tasks = SortedFilteredList<Task>(initialPredicate = {
        it.active
    })

    init {
        transaction {
            tasks.addAll(Task.all())
        }
    }

    var activeSession: WorkingSession? = null
    var activeView: TaskViewElement? = null

    fun getTasks(): ObservableList<Task> {
        return tasks.items
    }

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
                logger.info("Create new WorkingSession for Task {}", task.id.value)
                WorkingSession.new {
                    this.task = task
                }
            } else {
                latestSession
            }
        }
    }

    fun startWorkingSession(workingSession: WorkingSession, taskViewElement: TaskViewElement): WorkingSession {
        transaction {
            logger.info("Stop session of Task {}", activeSession?.task?.name)
            logger.info("Start session of Task {}", workingSession.task.name)
            activeSession?.stopSession()
            activeSession = workingSession.startSession()
        }
        activeView?.endSession()
        activeView = taskViewElement
        return workingSession
    }

    fun stopWorkingSession(workingSession: WorkingSession): WorkingSession {
        this.activeSession = null
        return transaction {
            logger.info("Stop session of Task {}", workingSession.task.name)
            workingSession.stopSession()
            if (workingSession.getElapsedTime().seconds < 5) {
                workingSession.delete()
            }
            return@transaction WorkingSession.new {
                this.task = workingSession.task
            }
        }
    }

    fun setActiveWorkingSession(workingSession: WorkingSession, taskViewElement: TaskViewElement) {
        if (logger.isInfoEnabled) {
            transaction {
                logger.info("Active session of task {}", workingSession.task.name)
            }
        }
        assert(activeSession == null)
        assert(activeView == null)
        this.activeSession = workingSession
        this.activeView = taskViewElement
    }

    fun stopActiveSession() {
        activeSession?.let { stopWorkingSession(it) }
    }
}
