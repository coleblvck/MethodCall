package com.coleblvck.methodcall.ui.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun TextEditCard(
    icon: ImageVector? = null,
    iconContentDescription: String = "Text Edit Icon",
    displayText: State<String>,
    onEditCallback: (String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth(),

        colors = CardDefaults.elevatedCardColors(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary,
        ),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            if (icon != null) {
                Icon(
                    icon,
                    contentDescription = iconContentDescription,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
            BasicTextField(
                value = displayText.value,
                onValueChange = { onEditCallback(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .weight(1f),
                visualTransformation = VisualTransformation.None,
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onTertiary,
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.onTertiary)
            )
        }
    }
}