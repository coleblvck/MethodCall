package com.coleblvck.methodcall.data.repositories

import androidx.lifecycle.LiveData
import com.coleblvck.methodcall.data.chain.Chain


interface ChainRepository {
    fun getChains(): LiveData<List<Chain>>
    fun insertChains(chains: List<Chain>)
    fun insertChain(chain: Chain)
    fun getChainByPhoneNumber(phoneNumber: String): List<Chain>
    fun deleteChainByPhoneNumber(phoneNumber: String)
}