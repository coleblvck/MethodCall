package com.coleblvck.methodcall.data.chain.chainToolBox

import androidx.compose.runtime.mutableStateListOf
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.repositories.ChainRepository
import com.coleblvck.methodcall.methodType.MethodType

class ChainToolBox(private val chainRepository: ChainRepository) {

    private fun saveChain(chain: Chain) {
        chainRepository.insertChain(chain)
    }

    private fun saveOrOverwriteChain(
        phoneNumber: String,
        methodChain: List<Pair<MethodType, List<String>>>
    ) {
        val chain = Chain(
            phoneNumber = phoneNumber,
            methodChain = methodChain
        )
        saveChain(chain)
    }

    fun getPhoneNumberChain(phoneNumber: String): Chain? {
        val chainOrNot = chainRepository.getChainByPhoneNumber(phoneNumber)
        return if (chainOrNot.isNotEmpty()) {
            chainOrNot[0]
        } else {
            null
        }
    }

    fun deletePhoneNumberChain(phoneNumber: String) {
        chainRepository.deleteChainByPhoneNumber(phoneNumber)
    }

    fun createOrReplaceChainItem(
        phoneNumber: String,
        method: MethodType,
        params: MutableList<String> = mutableStateListOf(),
        chainReplacementIndex: Int? = null
    ) {
        val chainItem = Pair<MethodType, List<String>>(
            method, params
        )
        val numberFilter = chainRepository.getChainByPhoneNumber(phoneNumber)
        if (numberFilter.isNotEmpty()) {
            val numberChain = numberFilter[0]
            val numberMethodChain = numberChain.methodChain
            val mutableNumberMethodChain = numberMethodChain.toMutableList()
            if (chainReplacementIndex != null) {
                mutableNumberMethodChain[chainReplacementIndex] = chainItem
                saveOrOverwriteChain(
                    phoneNumber = phoneNumber,
                    methodChain = mutableNumberMethodChain
                )
            } else {
                mutableNumberMethodChain.add(chainItem)
                saveOrOverwriteChain(
                    phoneNumber = phoneNumber,
                    methodChain = mutableNumberMethodChain
                )
            }
        } else {
            saveOrOverwriteChain(
                phoneNumber = phoneNumber,
                methodChain = listOf(
                    chainItem
                )
            )
        }
    }

    fun removePhoneNumberChainItem(phoneNumber: String, chainItemIndex: Int) {
        val numberFilter = chainRepository.getChainByPhoneNumber(phoneNumber)
        if (numberFilter.isNotEmpty()) {
            val numberChain = numberFilter[0]
            val numberMethodChain = numberChain.methodChain
            val mutableNumberMethodChain = numberMethodChain.toMutableList()
            mutableNumberMethodChain.removeAt(chainItemIndex)
            if (mutableNumberMethodChain.isEmpty()) {
                deletePhoneNumberChain(phoneNumber)
            } else {
                val chainToSave = Chain(
                    phoneNumber = numberFilter[0].phoneNumber,
                    methodChain = mutableNumberMethodChain
                )
                saveChain(chainToSave)
            }
        }
    }
}