package com.coleblvck.methodcall.methodType.helpers

import com.coleblvck.methodcall.methodType.MethodType
import java.util.Locale

fun getLegibleMethodTypeName(methodType: MethodType): String {
    val nameList = methodType.name.split("_")
    val capitalizedList = mutableListOf<String>()
    for (characterSet in nameList) {
        capitalizedList.add(firstCharCapital(characterSet.lowercase()))
    }
    return capitalizedList.joinToString(" ")
}

fun firstCharCapital(string: String): String {
    return string.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
}