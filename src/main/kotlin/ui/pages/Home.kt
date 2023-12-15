package ui.pages

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Composable
fun Home(){
    val date = remember { System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 } // 30 days from now

    val now = LocalDate.now()
    val targetDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
    val daysUntil = Duration.between(now.atStartOfDay(), targetDate.atStartOfDay()).toDays().coerceIn(0, 99999)
    Text("距离${targetDate}还有${daysUntil}天")
}