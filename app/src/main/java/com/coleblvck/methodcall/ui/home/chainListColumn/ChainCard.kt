package com.coleblvck.methodcall.ui.home.chainListColumn

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.methodType.MethodType


@Composable
fun ChainCard(
    phoneNumber: String,
    chain: List<Pair<MethodType, List<String>>>,
    getChainItemParameterName: (MethodType, String) -> String,
    removeChainItem: (String, Int) -> Unit,
    deleteChain: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val expansionIconRotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f, label = "Expansion Icon Animation"
    )
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth().height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .clickable { isExpanded = !isExpanded }
                        .weight(1f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            text = if (phoneNumber != "") {
                                phoneNumber
                            } else {
                                "All Numbers"
                            },
                            textAlign = TextAlign.Left,
                            fontSize = 32.sp,
                            fontWeight = FontWeight(500)
                        )
                        Icon(
                            modifier = Modifier
                                .size(40.dp)
                                .rotate(expansionIconRotationState),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "$phoneNumber Chain Drop-Down Arrow"
                        )
                    }
                }
                ElevatedCard(
                    modifier = Modifier,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary
                    )
                ) {
                    Icon(
                        modifier = Modifier.aspectRatio(1f)
                            .clickable { deleteChain(phoneNumber) }
                            .size(30.dp).padding(8.dp),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "$phoneNumber Chain Deletion Icon",
                    )
                }
            }
            if (isExpanded) {
                chain.forEachIndexed { itemIndex, chainItem ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(40.dp),
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Arrow Down Icon"
                        )
                    }
                    ChainItemCard(
                        phoneNumber = phoneNumber,
                        chainItem = chainItem,
                        itemIndex = itemIndex,
                        getChainItemParameterName = getChainItemParameterName,
                        removeChainItem = removeChainItem
                    )
                }
            }
        }
    }
}