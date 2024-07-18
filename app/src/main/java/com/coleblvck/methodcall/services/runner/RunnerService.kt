package com.coleblvck.methodcall.services.runner

import android.content.Intent
import androidx.lifecycle.LifecycleService
import com.coleblvck.methodcall.data.observeOnce
import com.coleblvck.methodcall.data.repositories.LocalChainRepository

class RunnerService : LifecycleService() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val phoneNumber = intent?.getStringExtra("EXTRA_PHONE_NUMBER")
        if (phoneNumber != null) {
            val localChainRepository = LocalChainRepository(this.applicationContext)
            localChainRepository.getChains().observeOnce(this) {
                if (it.isNotEmpty()) {
                    RunnerHandler.handleExecution(
                        context = this.applicationContext,
                        phoneNumber = phoneNumber,
                        chains = it
                    )
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}