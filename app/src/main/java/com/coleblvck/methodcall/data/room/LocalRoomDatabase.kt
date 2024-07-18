package com.coleblvck.methodcall.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.coleblvck.methodcall.data.chain.Chain
import com.coleblvck.methodcall.data.chain.ChainConverters
import com.coleblvck.methodcall.data.chain.ChainDao


@Database(entities = [Chain::class], version = 1)
@TypeConverters(ChainConverters::class)
abstract class LocalRoomDatabase: RoomDatabase() {
    abstract fun chainDao(): ChainDao
}