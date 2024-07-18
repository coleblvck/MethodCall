package com.coleblvck.methodcall.ui.home.methodTypeListColumn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coleblvck.methodcall.data.appPackage.App
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.methodType.helpers.getLegibleMethodTypeName
import com.coleblvck.methodcall.ui.common.composables.methodAddEditDialog.MethodAddEditDialog

@Composable
fun MethodTypeListItemCard(
    chainToolBox: ChainToolBox,
    methodType: MethodType,
    apps: State<List<App>>,
    modifier: Modifier = Modifier
) {
    var addDialogVisible by remember {
        mutableStateOf(false)
    }
    when {
        addDialogVisible -> MethodAddEditDialog(
            dismissCallback = { addDialogVisible = false },
            chainToolBox = chainToolBox,
            methodType = methodType,
            apps = apps
        )
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        onClick = { addDialogVisible = true }
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = getLegibleMethodTypeName(methodType),
            textAlign = TextAlign.Center,
            fontSize = 32.sp,
            fontWeight = FontWeight(500)
        )
    }
}