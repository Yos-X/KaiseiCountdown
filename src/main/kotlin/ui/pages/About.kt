package ui.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.x.Title

@Composable
fun About() {
    Title("关于", "快晴倒计时") {
        Row(modifier = Modifier.padding(horizontal = 30.dp).padding(bottom = 30.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                AboutHeadline("软件信息")
                Column {
                    AboutItem("软件名称", "快晴倒计时软件")
                    AboutItem("软件版本", "1.0.0")
                }
                Spacer(modifier = Modifier.height(20.dp))
                AboutHeadline("版权信息")
                Column {
                    AboutItem("开发商", "Yos-X")
                    AboutItem("官网", "https://yos-x.github.io/")
                    AboutItem("版权", "Copyright 2023-2024 枫灵科技 All rights reserved.")
                }
                Spacer(modifier = Modifier.height(20.dp))
                AboutHeadline("备注")
                Column {
                    AboutItem("基于", "Compose Multiplatform")
                    AboutItem("官网", "https://www.jetbrains.com/zh-cn/lp/compose-multiplatform/")
                }
            }
            Spacer(modifier = Modifier.fillMaxHeight().width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                AboutHeadline("开发者信息")
                Surface(/*shape = YosRoundedCornerShape(12.dp), tonalElevation = 2.dp, */modifier = Modifier.fillMaxSize()) {
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource("sakura.png"),
                            contentDescription = "sakura",
                            modifier = Modifier.size(250.dp).alpha(0.1f)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize().padding(bottom = 70.dp)
                    ) {
                        Image(
                            painter = painterResource("yosx.jpg"),
                            contentDescription = "yosx",
                            modifier = Modifier.size(120.dp).border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                                CircleShape
                            ).clip(CircleShape).clipToBounds()
                        )
                        Text(
                            "枫灵剑影",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Text("yos-studio@qq.com", modifier = Modifier.alpha(0.5f).padding(top = 1.dp), fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AboutItem(title: String, content: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(title, fontSize = 14.sp)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(content, modifier = Modifier.alpha(0.5f), fontSize = 14.sp)
        }
    }
}

@Composable
fun AboutHeadline(content: String) {
    Text(content, fontSize = 18.sp, modifier = Modifier.padding(bottom = 15.dp))
}