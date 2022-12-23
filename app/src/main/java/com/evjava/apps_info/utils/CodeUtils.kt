package com.evjava.apps_info.utils

object CodeUtils {
    inline fun Boolean.doIf(callback: () -> Unit) = apply { if (this) callback() }

    fun startWhileTrueThread(sleepMillis: Long, callback: () -> Unit) {
        Thread {
            while (true) {
                callback()
                try {
                    Thread.sleep(sleepMillis)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}