package com.coleblvck.methodcall.methodType.definitions.reboot

val reboot: (arg: List<Any>) -> Unit = {
    try {
        val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "reboot"))
        process.waitFor()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}