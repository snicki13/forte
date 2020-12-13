package de.snickit.forte.view

import de.snickit.forte.controller.ForteController
import de.snickit.forte.model.Task
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.ColorPicker
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.CornerRadii
import javafx.scene.layout.Priority
import javafx.scene.paint.Color
import tornadofx.*

import javafx.util.Duration.seconds
import org.slf4j.LoggerFactory
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit


class TaskViewElement(private val task: Task) : View("TaskViewElement") {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val forteController: ForteController by inject()
    private val colorPicker = ColorPicker(c(task.color)).addClass(Styles.largeRectPicker)

    private var workingSession = forteController.getLatestOrNewWorkingSession(task)

    val label = label {
        text = getDurationBreakdown(workingSession.getElapsedTimeInSeconds())
    }

    val timeline = timeline(false) {
        cycleCount = Timeline.INDEFINITE
        keyFrames += KeyFrame(seconds(1.0), EventHandler {
            label.text = getDurationBreakdown(workingSession.getElapsedTimeInSeconds())
        })
        if (workingSession.startingTime != null) {
            play()
        }
    }

    override val root = borderpane {
        this.addClass(Styles.taskViewElement)

        this.backgroundProperty().bind(Bindings.createObjectBinding( Callable {
            val fill = BackgroundFill(colorPicker.value, CornerRadii.EMPTY, Insets.EMPTY)
            Background(fill)
        }, colorPicker.valueProperty()))

        style {
            borderRadius = multi(box(10.px))
            backgroundRadius = multi(box(10.px))
        }
        top = hbox {
            add(label(task.getTitle()))
            paddingAll = 2.0
            add(region { hgrow = Priority.ALWAYS })
            add(colorPicker)

        }
        center = label
        bottom = hbox {
            paddingBottom = 5.0
            region { hgrow = Priority.ALWAYS }
            button {
                text = if (workingSession.startingTime != null) "Stop" else "Start"
                this.apply {
                    alignment = Pos.CENTER
                }
                this.onAction = EventHandler {
                    if (workingSession.startingTime != null) {
                        timeline.stop()
                        this.text = "Start"
                        workingSession = forteController.stopWorkingSession(workingSession)
                    } else {
                        timeline.play()
                        this.text = "Stop"
                        workingSession = forteController.startWorkingSession(workingSession)
                    }
                }
            }
            region { hgrow = Priority.ALWAYS }
        }
    }

    fun getDurationBreakdown(seconds: Long): String {
        var secs = seconds
        require(secs >= 0) { "Duration must be greater than zero!" }
        val hours = TimeUnit.SECONDS.toHours(secs)
        secs -= TimeUnit.HOURS.toSeconds(hours)
        val minutes = TimeUnit.SECONDS.toMinutes(secs)
        secs -= TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

}
