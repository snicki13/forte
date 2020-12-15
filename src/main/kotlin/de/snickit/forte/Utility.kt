package de.snickit.forte

import java.time.Duration
import java.util.concurrent.TimeUnit

object Utility {

    fun Duration.getElapsedTimeString(): String {
        var secs = this.seconds
        val hours = TimeUnit.SECONDS.toHours(secs)
        secs -= TimeUnit.HOURS.toSeconds(hours)
        val minutes = TimeUnit.SECONDS.toMinutes(secs)
        secs -= TimeUnit.MINUTES.toSeconds(minutes)

        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}