package ru.cyclone.cycfinans.domain.usecases

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
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
        description = "It's time to remember about your tasks"
    }

    private fun createNotificationManager(context: Context) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun createNotificationBuilder(context: Context) {
        builder = NotificationCompat.Builder(context, "REMINDER").apply {
            setContentTitle("Reminder")
            setContentText("It's time to remember about your tasks")
            setSmallIcon(R.drawable.icon)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setCategory(NotificationCompat.CATEGORY_ALARM)
            priority = NotificationCompat.PRIORITY_MAX
        }.build()
    }

    fun launchSingleNotification() {
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