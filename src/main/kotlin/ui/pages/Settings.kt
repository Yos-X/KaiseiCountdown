package ui.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.x.Title

@Composable
fun Settings() {
    Title("设置", "快晴倒计时") {
        Text("这里还没有内容。", modifier = Modifier.padding(horizontal = 30.dp))
    }
}