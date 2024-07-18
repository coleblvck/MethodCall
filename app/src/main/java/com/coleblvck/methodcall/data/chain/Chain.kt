package com.coleblvck.methodcall.data.chain

import android.util.Log
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.coleblvck.methodcall.methodType.MethodType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "chain")
data class Chain(
    @ColumnInfo(name = "phone_number") @PrimaryKey val phoneNumber: String,
    @ColumnInfo(name = "method_chain") var methodChain: List<Pair<MethodType, List<String>>>
)

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)

class ChainConverters {

    private fun stringFromMethodType(value: MethodType): String {
        return value.name
    }

    private fun methodTypeFromString(value: String): MethodType {
        return MethodType.valueOf(value)
    }


    @TypeConverter
    fun fromChain(value: List<Pair<MethodType, List<String>>>): String {
        val newList = mutableListOf<Pair<String, List<String>>>()
        for (item in value) {
            val (type, methods) = item
            val newPair = Pair(stringFromMethodType(type), methods)
            newList.add(newPair)
        }
        return Gson().toJson(newList)
    }

    @TypeConverter
    fun toChain(value: String): List<Pair<MethodType, List<String>>> {
        return try {
            val rawStoreVal = Gson().fromJson<List<Pair<String, List<String>>>>(value)
            val newList = mutableListOf<Pair<MethodType, List<String>>>()
            for (item in rawStoreVal) {
                val (methodString, methods) = item
                val newPair = Pair(methodTypeFromString(methodString), methods)
                newList.add(newPair)
            }
            return newList
        } catch (e: Exception) {
            Log.e("", "")
            listOf()
        }
    }
}