package com.evjava.apps_info

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.evjava.apps_info.MainActivity.Companion.SHOW_INSTALLED_KEY
import com.evjava.apps_info.MainActivity.Companion.TIMESTAMP_KEY
import com.evjava.apps_info.PackageManagerExt.getInstalledAppsFrom
import com.evjava.apps_info.impl.data.AppItem
import com.evjava.apps_info.utils.CodeUtils.startWhileTrueThread
import io.github.aakira.napier.Napier

class AppListenerService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Napier.i { "#onStartCommand" }
        val curTimestamp = System.currentTimeMillis()
        val (seqNumber, _) = application.packageManager.getInstalledAppsFrom(curTimestamp, 0)

        startWhileTrueThread(sleepMillis = REFRESH_APPS_INTERVAL_MILLIS) {
            Napier.i { "Service is running ($seqNumber)..." }
            val (_, newApps) = application.packageManager.getInstalledAppsFrom(curTimestamp, seqNumber)
            startForeground(NOTIFICATION_ID, buildNotification(curTimestamp, newApps))
        }
        val notification = buildNotification(0, emptyList())
        startForeground(NOTIFICATION_ID, notification)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun buildNotification(timestamp: Long, updates: List<AppItem>): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID, NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }

        val (title, text) = when (updates.size) {
            0 -> "AppsInfo Tracker (no new apps)..." to null
            else -> {
                val appForm = if (updates.size == 1) "app" else "apps"
                val packagesList = updates.joinToString { "\"${it.appName}\"" }
                "AppsInfo Tracker (${updates.size} new $appForm)...\n" to packagesList
            }
        }

        val resultIntent = Intent(this, MainActivity::class.java).apply {
            action = SHOW_INSTALLED_KEY
            putExtra(TIMESTAMP_KEY, timestamp)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val mutableFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
        val flags = PendingIntent.FLAG_UPDATE_CURRENT or mutableFlag
        val pendingIntent = PendingIntent.getActivity(this, 0, resultIntent, flags)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(pendingIntent)
            .build()
    }

    companion object {
        const val REFRESH_APPS_INTERVAL_MILLIS = 15_000L
        const val CHANNEL_ID = "Foreground Service ID"
        var NOTIFICATION_ID = 1001
    }
}