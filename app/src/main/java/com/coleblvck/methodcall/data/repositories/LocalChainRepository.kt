package com.coleblvck.methodcall.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.coleblvck.methodcall.data.DatabaseAgent
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.ChainDao


class LocalChainRepository(context: Context): ChainRepository {
    private val chainDao: ChainDao = DatabaseAgent.getLocalInstance(context).chainDao()

    override fun getChains(): LiveData<List<Chain>> = chainDao.getAll()

    override fun insertChains(chains: List<Chain>) = chainDao.insertAll(chains)

    override fun insertChain(chain: Chain) = chainDao.insert(chain)

    override fun getChainByPhoneNumber(phoneNumber: String): List<Chain> = chainDao.getByPhoneNumber(phoneNumber)

    override fun deleteChainByPhoneNumber(phoneNumber: String) = chainDao.deleteByPhoneNumber(phoneNumber)

}