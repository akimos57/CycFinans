package ru.cyclone.cycfinans.domain.usecases

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import ru.cyclone.cycnote.R

object NotificationController {
    private var notificationManager : NotificationManager? = null
    private var builder: Notification? = null
    private val channel = NotificationChannel(
        "REMINDER",
        "Reminder",
        NotificationManager.IMPORTANCE_HIGH
    ).apply {
        description = "IT’S TIME TO REMEMBER ABOUT YOUR TASKS"
    }

    private fun createNotificationManager(context: Context) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun createNotificationBuilder(context: Context, text: String = "It's time to remember about your tasks") {
        builder = NotificationCompat.Builder(context, "REMINDER").apply {
            setContentTitle("Reminder")
            setContentText(text)
            setSmallIcon(R.drawable.icon)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()
    }

    fun launchSingleNotification(context: Context? = null, text: String? = null) {
        if (!text.isNullOrBlank() and (context != null)) {
            createNotificationBuilder(context!!, text!!)
        }
        notificationManager?.notify(0, builder)
    }

    fun provide(context: Context) {
        if (notificationManager == null) {
            createNotificationManager(context)
            notificationManager?.createNotificationChannel(channel)
            createNotificationBuilder(context)
        }
    }
}

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.getStringExtra("Notification Text").isNullOrBlank()) {
            NotificationController.launchSingleNotification(context, intent?.getStringExtra("Notification Text"))
        } else {
            NotificationController.launchSingleNotification()
        }
    }
}