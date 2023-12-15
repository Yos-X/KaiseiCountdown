package utils

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class YosTimer {
    companion object {
        fun dateToStamp(date: String): Long {
            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            val localDate = LocalDate.parse(date, formatter)
            return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }

        fun getBetweenDays(date: Long): Long {
            val now = LocalDate.now()
            val targetDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
            return Duration.between(now.atStartOfDay(), targetDate.atStartOfDay()).toDays().coerceIn(0, 99999)
        }
    }
}