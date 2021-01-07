package de.snickit.forte

import javafx.scene.paint.Color
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object Utility {

    private val prop = Properties()
    init {
        ClassLoader.getSystemResourceAsStream("database.properties").use {
            prop.load(it)
        }
    }

    fun getProperty(key: String): String = prop.getProperty(key)

    fun Duration.getElapsedTimeString(): String {
        var secs = this.seconds
        val hours = TimeUnit.SECONDS.toHours(secs)
        secs -= TimeUnit.HOURS.toSeconds(hours)
        val minutes = TimeUnit.SECONDS.toMinutes(secs)
        secs -= TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    fun Color.toHexString(): String {
        val r = (this.red * 255).roundToInt() shl 24
        val g = (this.green * 255).roundToInt() shl 16
        val b = (this.blue * 255).roundToInt() shl 8
        val a = (this.opacity * 255).roundToInt()
        return String.format("#%08X", r + g + b + a)
    }

    fun <T> Iterable<T>.sumDuration(selector: (T) -> Duration): Duration {
        var sum = Duration.ZERO
        for (element in this) {
            sum = sum.plus(selector(element))
        }
        return sum
    }
}
