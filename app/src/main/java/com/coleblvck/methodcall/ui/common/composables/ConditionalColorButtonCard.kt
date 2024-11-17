package com.coleblvck.methodcall.ui.common.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ConditionalColorButtonCard(
    modifier: Modifier = Modifier,
    selectionColorCondition: Boolean,
    clickAction: () -> Unit = {},
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxSize(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (selectionColorCondition) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.background
            },
            contentColor = if (selectionColorCondition) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onBackground
            },
        ),
        onClick = clickAction
    ) {
        content()
    }
}