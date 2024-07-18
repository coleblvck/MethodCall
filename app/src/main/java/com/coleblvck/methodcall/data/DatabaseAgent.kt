package com.coleblvck.methodcall.data

import android.content.Context
import androidx.room.Room
import com.coleblvck.methodcall.data.room.LocalRoomDatabase

class DatabaseAgent private constructor() {
    companion object {
        @Volatile
        private var localInstance: LocalRoomDatabase? = null

        fun getLocalInstance(context: Context): LocalRoomDatabase =
            localInstance ?: synchronized(this) {
                localInstance ?: Room.databaseBuilder(
                    context.applicationContext,
                    LocalRoomDatabase::class.java,
                    "chain_database"
                ).allowMainThreadQueries().build().also { localInstance = it }
            }
    }
}