package com.coleblvck.methodcall.ui.common.composables

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnWithHeading(
    modifier: Modifier = Modifier,
    scrollEnabled: Boolean = true,
    scrollState: ScrollState = rememberScrollState(),
    headingText: String? = null,
    headingTextSize: TextUnit = 32.sp,
    topSpace: Dp = 100.dp,
    subHeadingText: String? = null,
    subHeadingTextSize: TextUnit = 20.sp,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .verticalScroll(
                state = scrollState,
                enabled = scrollEnabled
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (headingText != null) {
            Spacer(
                modifier = Modifier.height(topSpace)
            )
            Text(
                text = headingText,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = headingTextSize,
                fontWeight = FontWeight(500)
            )
            if (subHeadingText != null) {
                Text(
                    text = subHeadingText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(align = Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontSize = subHeadingTextSize,
                    fontWeight = FontWeight(400),
                )
            }
        }
        content()
    }
}