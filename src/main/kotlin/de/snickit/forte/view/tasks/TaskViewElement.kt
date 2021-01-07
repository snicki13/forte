package de.snickit.forte.view.tasks

import de.snickit.forte.Utility.getElapsedTimeString
import de.snickit.forte.controller.ForteController
import de.snickit.forte.persistence.Task
import de.snickit.forte.persistence.WorkingSession
import de.snickit.forte.view.Styles
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*

import javafx.util.Duration.seconds
import org.slf4j.LoggerFactory

class TaskViewElement(private val task: Task) : View("TaskViewElement") {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val forteController: ForteController by di()

    private var workingSession: WorkingSession
    private val label: Label
    private val timeline: Timeline
    private val startStopButton: Button

    init {
        workingSession = forteController.getLatestOrNewWorkingSession(task)
        if (workingSession.startingTime != null) {
            task.active = true
            forteController.setActiveWorkingSession(workingSession, this)
        }
        label = label {
            text = workingSession.getElapsedTime().getElapsedTimeString()
        }
        timeline = timeline(false) {
            cycleCount = Timeline.INDEFINITE
            keyFrames += KeyFrame(seconds(1.0), EventHandler {
                label.text = workingSession.getElapsedTime().getElapsedTimeString()
            })
            if (task.active) {
                play()
            }
        }
        startStopButton = getStartStopButton()
    }

    override val root = borderpane {
        this.addClass(Styles.taskViewElement)

        style {
            borderRadius = multi(box(10.px))
            backgroundRadius = multi(box(10.px))
            backgroundColor = multi(Color.web(task.color))
        }
        top = hbox {
            add(label(task.getTitle()))
            paddingAll = 2.0
            add(region { hgrow = Priority.ALWAYS })
            add(hbox {
                button {
                    addClass(Styles.removeButton)
                    graphic = Styles.removeIcon()
                }
                button {
                    addClass(Styles.removeButton)
                    graphic = Styles.editIcon()
                }
            })
        }
        center = label
        bottom = hbox {
            paddingBottom = 5.0
            region { hgrow = Priority.ALWAYS }
            add(startStopButton)
            region { hgrow = Priority.ALWAYS }
        }
    }

    private fun startSession() {
        workingSession = forteController.startWorkingSession(workingSession, this)
        timeline.play()
        startStopButton.graphic = Styles.getStopButton()
        task.active = true
    }

    fun endSession() {
        workingSession = forteController.stopWorkingSession(workingSession)
        timeline.stop()
        startStopButton.graphic = Styles.getStartButton()
        task.active = false
    }

    private fun getStartStopButton() = button {
        addClass(Styles.startStopButton)
        graphic = if (task.active) {
            Styles.getStopButton()
        } else Styles.getStartButton()
        this.onAction = EventHandler {
            if (task.active) {
                logger.info("STOP")
                endSession()
            } else {
                logger.info("START")
                startSession()
            }
        }
    }

}
