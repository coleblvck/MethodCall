package com.coleblvck.methodcall.services.runner

import android.content.Context
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.methodType.MethodType

class RunnerHandler {
    companion object {
        fun handleExecution(
            context: Context,
            phoneNumber: String,
            chains: List<Chain>
        ) {
            val filteredChainList = chains.filter { it.phoneNumber == phoneNumber || it.phoneNumber == "" }
            if (filteredChainList.isNotEmpty()) {
                for (chain in filteredChainList) {
                    organizeExecution(context, chain.methodChain)
                }
            }
        }

        private fun organizeExecution(
            context: Context,
            methods: List<Pair<MethodType, List<String>>>
        ) {
            for (method in methods) {
                execute(context, method.first, method.second)
            }
        }

        private fun execute(context: Context, methodType: MethodType, args: List<String>) {
            when (methodType) {
                MethodType.PACKAGE_LAUNCH -> {
                    if (args.isNotEmpty()) {
                        args.forEach { packageName: String ->
                            MethodType.PACKAGE_LAUNCH.execute(listOf(context, packageName))
                        }
                    }
                }

                MethodType.TOGGLE_TORCH -> MethodType.TOGGLE_TORCH.execute(listOf(context))
            }
        }
    }
}