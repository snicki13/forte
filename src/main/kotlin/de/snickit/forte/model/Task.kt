package de.snickit.forte

import javafx.beans.property.Property
import javafx.beans.property.StringProperty
import javafx.scene.paint.Color
import tornadofx.*
import java.time.LocalDate
import java.util.*
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource

@ExperimentalTime
class Task() {

    constructor(name: String): this() {
        this.name = name

    }

    var name: String by property<String>()
    fun nameProperty() = getProperty(Task::name)

    val timeSource = TimeSource.Monotonic

    var lastStart: TimeMark by property<TimeMark>()
    var beforeDuration: Duration by property<Duration>()

    var currentDuration: Duration = lastStart.elapsedNow()
    fun currentTimeProperty() = getProperty(Task::currentDuration)

    override fun toString(): String = name
}

@ExperimentalTime
class TaskModel : ItemViewModel<Task>(Task()) {
    val name: StringProperty = bind { item?.nameProperty() }
    val currentTime: Property<Duration> = bind { item?.currentTimeProperty() }
}
