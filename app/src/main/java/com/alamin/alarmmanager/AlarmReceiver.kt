package com.alamin.alarmmanager

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*
import java.util.concurrent.TimeUnit

private const val TAG = "AlarmReceiver"
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val timeInMillis = intent.getLongExtra(Constants.SET_EXTRA_EXACT_ALARM,0L)
        if (intent.action == Constants.SET_EXACT_ALARM){
            sendNotification(context,"Exact Alarm",timeInMillis);
        }else{
            val  cal = Calendar.getInstance().apply {
                this.timeInMillis = timeInMillis + TimeUnit.DAYS.toMillis(7)
            }
            AlarmService(context).setRepetitiveAlarm(cal.timeInMillis)
            sendNotification(context,"Repeat Alarm",timeInMillis);
        }

    }

    private fun sendNotification(context: Context, title: String, timeInMillis: Long) {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context,
            Constants.NOTIFICATION_CHANNEL
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText("Message")
            .setAutoCancel(true)
            .setOngoing(false)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)
        val resultIntent = Intent(context, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0)
        mBuilder.setContentIntent(resultPendingIntent)
        notificationManager.notify(999,mBuilder.build())
    }
}