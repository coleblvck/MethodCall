package com.coleblvck.methodcall.data.chain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChainDao {
    @Query("SELECT * FROM chain")
    fun getAll(): LiveData<List<Chain>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(chains: List<Chain>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(chain: Chain)

    @Query("SELECT * FROM chain WHERE phone_number = :phoneNumber")
    fun getByPhoneNumber(phoneNumber: String): List<Chain>

    @Query("DELETE FROM chain WHERE phone_number = :phoneNumber")
    fun deleteByPhoneNumber(phoneNumber: String)
}