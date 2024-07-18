package com.coleblvck.methodcall.ui.home.chainListColumn

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.helpers.getLegibleMethodTypeName

@Composable
fun ChainItemCard(
    phoneNumber: String,
    chainItem: Pair<MethodType, List<String>>,
    itemIndex: Int,
    getChainItemParameterName: (MethodType, String) -> String,
    removeChainItem: (String, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = getLegibleMethodTypeName(chainItem.first),
                    textAlign = TextAlign.Left,
                    fontSize = 32.sp,
                    fontWeight = FontWeight(500)
                )

                Icon(
                    modifier = Modifier.size(40.dp).clickable {
                        removeChainItem(phoneNumber, itemIndex)
                    },
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon for ${getLegibleMethodTypeName(chainItem.first)}"
                )
            }
            if (chainItem.second.isNotEmpty()) {
                HorizontalDivider()
                chainItem.second.forEach {
                    val nameToggled = remember {
                        mutableStateOf(false)
                    }
                    Card(
                        onClick = { nameToggled.value = !nameToggled.value },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            text = if (nameToggled.value) {
                                it
                            } else {
                                getChainItemParameterName(chainItem.first, it)
                            },
                            textAlign = TextAlign.Center,
                            fontSize = 24.sp,
                            fontWeight = FontWeight(500)
                        )
                    }
                }
            }
        }
    }
}