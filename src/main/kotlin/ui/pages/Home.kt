package ui.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import errorDialog
import errorMessage
import kotlinx.coroutines.delay
import ui.Screen
import ui.goScreen
import ui.x.Title
import utils.Kaisei
import utils.YosTimer
import yosNav

@Composable
fun Home() {
    Title("大事项", "快晴倒计时") {
        /*val date = remember { System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30 } // 30 days from now

        val now = LocalDate.now()
        val targetDate = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
        val daysUntil = Duration.between(now.atStartOfDay(), targetDate.atStartOfDay()).toDays().coerceIn(0, 99999)
        Text("距离${targetDate}还有${daysUntil}天")*/
        val kaisei = Kaisei()
        val countdownExist = rememberSaveable {
            mutableStateOf(false)
        }
        val eventName = rememberSaveable {
            mutableStateOf("大事项")
        }
        val daysUntil = rememberSaveable {
            mutableStateOf(0L)
        }
        LaunchedEffect(Unit) {
            while (true) {
                try {
                    countdownExist.value = kaisei.countdownExist()
                    if (countdownExist.value) {
                        val countdownData = kaisei.getCountdown()!!
                        eventName.value = countdownData.eventName
                        daysUntil.value = YosTimer.getBetweenDays(YosTimer.dateToStamp(countdownData.date))
                    }
                } catch (e: Exception) {
                    errorDialog.value = true
                    errorMessage.value = e.toString()
                }
                delay(1000L)
            }
        }
        if (countdownExist.value) {
            Surface(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("距离", fontWeight = FontWeight.Light, fontSize = 60.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            eventName.value,
                            fontWeight = FontWeight.Bold,
                            fontSize = 50.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            "还有",
                            fontWeight = FontWeight.Light,
                            fontSize = 45.sp,
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            daysUntil.value.toString(),
                            fontWeight = FontWeight.Light,
                            fontSize = 70.sp,
                            modifier = Modifier.padding(end = 10.dp).padding(bottom = 5.dp)
                        )
                        Text("天", fontWeight = FontWeight.Light, fontSize = 45.sp)
                    }
                }
            }
            Column(modifier = Modifier.padding(horizontal = 30.dp).weight(1f)) {
                Text("事项已配置。")
                Spacer(modifier = Modifier.height(2.dp))
                Text("修改事项配置", color = MaterialTheme.colorScheme.tertiary, fontSize = 15.sp,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        goScreen(yosNav, Screen.AddCountdown)
                    })
            }
        } else {
            Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                Text("尚未配置任何事项。")
                Spacer(modifier = Modifier.height(2.dp))
                Text("配置新的事项", color = MaterialTheme.colorScheme.tertiary, fontSize = 15.sp,
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        goScreen(yosNav, Screen.AddCountdown)
                    })
            }
        }
    }
}