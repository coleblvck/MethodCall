package com.coleblvck.methodcall.methodType.definitions.shutdown

val shutdown: (arg: List<Any>) -> Unit = {
    try {
        val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "reboot -p"))
        process.waitFor()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}