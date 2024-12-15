package com.coleblvck.methodcall.ui.home.chainListColumn

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.chainToolBox.ChainToolBox
import com.coleblvck.methodcall.methodType.MethodType
import com.coleblvck.methodcall.ui.common.composables.ColumnWithHeading

@Composable
fun ChainListColumn(
    chainToolBox: ChainToolBox,
    liveChains: LiveData<List<Chain>>,
    getChainItemParameterName: (MethodType, String) -> String,
) {
    val chains: MutableState<List<Chain>> = remember {
        mutableStateOf(listOf())
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    val liveChainObserver = Observer<List<Chain>> { chainsObserved ->
        chains.value = chainsObserved
    }

    LaunchedEffect (Unit) {
        liveChains.observe(lifecycleOwner, liveChainObserver)
    }

    ColumnWithHeading(
        modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
        headingText = "Chains",
        subHeadingText = if (chains.value.isEmpty()) {
            "You have no existing chain."
        } else {
            null
        }
    ) {
        if (chains.value.isNotEmpty()) {
            chains.value.forEach { (phoneNumber: String, chain: List<Pair<MethodType, List<String>>>) ->
                ChainCard(
                    phoneNumber = phoneNumber,
                    chain = chain,
                    getChainItemParameterName = getChainItemParameterName,
                    removeChainItem = chainToolBox::removePhoneNumberChainItem,
                    deleteChain = chainToolBox::deletePhoneNumberChain
                )
            }
        }
    }
}