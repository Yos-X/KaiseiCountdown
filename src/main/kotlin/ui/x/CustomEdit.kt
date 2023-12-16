package ui.x

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ui.YosRoundedCornerShape

/**
 * 自定义编辑框控件
 * @param text 显示的文本
 * @param onValueChange 编辑框文本变更事件
 * @param hint 空字符时的提示
 * @param enabled 是否可用
 * @param readOnly 是否只读
 * @param singleLine 是否单行
 * @param textStyle 文本样式
 * @param visualTransformation 文本类型
 * @param keyboardOptions 键盘配置选项
 * @param keyboardActions 键盘配置动作
 *
 */
@Composable
fun CustomEdit(
    modifier: Modifier? = Modifier,
    text: String = "",
    onValueChange: (String) -> Unit,
    hint: String = "Input…",
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.titleSmall,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        singleLine = singleLine,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        label = {
            Text(text = hint)
        },
        modifier = modifier?.fillMaxWidth()?.height(60.dp) ?: Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
            unfocusedLabelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        ),
        shape = YosRoundedCornerShape(16.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}