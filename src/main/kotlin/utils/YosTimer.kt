package utils

import errorDialog
import errorMessage
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class YosTimer {
    companion object {
        fun dateToStamp(date: String): Long {
            return try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val localDate = LocalDate.parse(date, formatter)
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() + 28800000
            } catch (e: Exception) {
                errorDialog.value = true
                errorMessage.value = e.toString()
                0L
            }
        }

        fun stampToDate(stamp: Long): String {
            return Instant.ofEpochMilli(stamp).atZone(ZoneId.systemDefault()).toLocalDate().toString()
        }

        fun getBetweenDays(date: Long): Long {
            val now = LocalDate.now()
            val targetDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
            return Duration.between(now.atStartOfDay(), targetDate.atStartOfDay()).toDays().coerceIn(0, 99999)
        }
    }
}