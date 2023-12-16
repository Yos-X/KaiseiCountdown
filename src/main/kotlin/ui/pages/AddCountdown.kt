package ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.YosRoundedCornerShape
import ui.popBack
import ui.x.CustomEdit
import ui.x.Title
import utils.Kaisei
import utils.YosTimer
import yosNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCountdown() {
    val errorDialog = mutableStateOf(false)

    val kaisei = Kaisei()
    val nowTimeMills = System.currentTimeMillis()
    var initialSelectedDateMillis = nowTimeMills
    var eventName by rememberSaveable {
        mutableStateOf("")
    }

    if (kaisei.countdownExist()) {
        val countdownData = kaisei.getCountdown()!!
        initialSelectedDateMillis = YosTimer.dateToStamp(countdownData.date)
        eventName = countdownData.eventName
    }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialSelectedDateMillis)


    Title("配置倒计时事项", "大事项", scrollable = true, coverContent = {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            ExtendedFloatingActionButton(
                text = {
                    Text("完成配置")
                },
                icon = {
                    Icon(Icons.Default.Done, "Done")
                },
                onClick = {
                    if (eventName.isBlank()) {
                        errorDialog.value = true
                    } else {
                        Kaisei().saveCountdown(
                            Kaisei.CountdownData(
                                eventName.trim(),
                                YosTimer.stampToDate(datePickerState.selectedDateMillis!!)
                            )
                        )
                        popBack(yosNav)
                    }
                },
                modifier = Modifier.padding(30.dp)
            )
        }
    }) {
        if (errorDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    errorDialog.value = false
                },
                icon = {
                    Icon(Icons.Default.Warning, "Warning")
                },
                title = {
                    Text(text = "提示")
                },
                text = {
                    Text(text = "请填写完整")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            errorDialog.value = false
                        }
                    ) {
                        Text("好")
                    }
                }
            )
        }

        Column(Modifier.fillMaxSize()) {
            Text(
                "键入必要的参数以完成事项配置。",
                modifier = Modifier.padding(horizontal = 30.dp).padding(bottom = 30.dp).alpha(0.7f)
            )

            Text("事项名称", modifier = Modifier.padding(horizontal = 30.dp).padding(bottom = 4.dp), fontSize = 18.sp)
            CustomEdit(
                text = eventName, onValueChange = {
                    eventName = it
                },
                hint = "事项名称",
                modifier = Modifier.padding(horizontal = 25.dp)
            )
            Text(
                "输入事项的名称，推荐使用长度适中的简称，如：中考",
                modifier = Modifier.padding(horizontal = 30.dp).padding(top = 2.dp).alpha(0.5f),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text("事项日期", modifier = Modifier.padding(horizontal = 30.dp).padding(bottom = 10.dp), fontSize = 18.sp)
            Surface(
                tonalElevation = 2.dp,
                modifier = Modifier.width(500.dp).padding(horizontal = 25.dp).padding(bottom = 30.dp),
                shape = YosRoundedCornerShape(12.dp)
            ) {
                DatePicker(state = datePickerState, modifier = Modifier.padding(bottom = 10.dp),
                    title = {
                        Text("选择日期", modifier = Modifier.padding(start = 24.dp, end = 12.dp, top = 24.dp))
                    }, showModeToggle = false,
                    dateValidator = {
                        it >= nowTimeMills
                    }
                )
            }
        }
    }
}