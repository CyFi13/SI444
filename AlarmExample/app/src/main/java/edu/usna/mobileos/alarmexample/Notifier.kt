package edu.usna.mobileos.alarmexample

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

//for API >= 26 (Android 8.0 - Oreo), create an actual channel with the provided ID
fun createNotificationChannel(context: Context, channelId: String="SI444 channel") {

    // Create the NotificationChannel, but only on API 26+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val name = "SI444 channel "
        val descriptionText = "channel for SI444 notifications"

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance)
        channel.description = descriptionText

        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}
@SuppressLint("MissingPermission")
fun generateNotification(context: Context, title: String, message: String, notificationId: Int, channelId: String = "SI444 channel") {

    var icon: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.notification_icon)

    //build notification
    var mBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.notification_icon)
        .setLargeIcon(icon)
        .setContentTitle(title)
        .setContentText(message)

    //set high priority so the notifications will appear as floating window on top of screen
    mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))

    mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL)

    // intent to specify which activity to launch when the
    // notification is selected
    val notificationIntent = Intent(
        context,
        MainActivity::class.java
    )

    // set intent so it does not start a new activity
    notificationIntent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP
            or Intent.FLAG_ACTIVITY_SINGLE_TOP)

    // pending intent to allow the system to launch the activity
    // inside our app from the notification
    val pendingIntent = PendingIntent.getActivity(
        context, 0,
        notificationIntent, PendingIntent.FLAG_IMMUTABLE
    )
    mBuilder.setContentIntent(pendingIntent)

    // set notification to cancel itself when selected
// as opposed to canceling it manually
    mBuilder.setAutoCancel(true)
    mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND)
    mBuilder.setDefaults(NotificationCompat.DEFAULT_VIBRATE)
    mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS)
    mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND or NotificationCompat.DEFAULT_VIBRATE)

//    mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL)

    mBuilder.setSound(
         Uri.parse(
             "android.resource://" +
                     context.getPackageName() + "/raw/lefreak"
         )
     )
    // customize vibration pattern
    // delay 50ms delay, vibrate 600ms, pause 500ms, vibrate 1200ms
    mBuilder.setVibrate(longArrayOf(50, 600, 500, 1200))

    mBuilder.setDefaults(NotificationCompat.DEFAULT_LIGHTS)

    // get instance of notification manager
    val notificationManager = NotificationManagerCompat.from(context)

    //send notification
    notificationManager.notify(notificationId, mBuilder.build())
}

fun cancelNotification(context: Context, notificationId: Int) {
    NotificationManagerCompat.from(context).cancel(notificationId)
}