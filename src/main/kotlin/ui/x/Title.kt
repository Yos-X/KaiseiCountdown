package ui.x

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Title(
    title: String,
    subTitle: String? = null,
    scrollable: Boolean = false,
    coverContent: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = (if (scrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier).fillMaxSize()) {
            Column(modifier = Modifier.padding(horizontal = 30.dp)) {
                Spacer(modifier = Modifier.height(38.dp))
                if (subTitle != null) {
                    Text(subTitle, fontWeight = FontWeight.Light, fontSize = 13.sp, modifier = Modifier.alpha(0.7f))
                }
                Text(title, fontWeight = FontWeight.Light, fontSize = 50.sp)
                Spacer(modifier = Modifier.height(15.dp))
            }
            content()
        }
        coverContent?.invoke()
    }
}