package edu.usna.mobileos.alarmexample

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize alarm manager
        val alarmManager = baseContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        //Create intent for action Alarm triggers
        val alarmIntent = Intent(baseContext, AlarmReceiver::class.java)

        //Pass intent as parameter to PendingIntent to allow predefined code to run
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel(this)

        //Set alarm to fire at given time (one minute from now)
        //alarmManager?.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 60 * 1000, pendingIntent)


        //Cancel Alarm
        //alarmManager?.cancel(pendingIntent)

        //Register Buttons
        val setButton: Button = findViewById(R.id.startButton)
        val cancelButton: Button = findViewById(R.id.stopButton)
        val createNotificationButton: Button = findViewById(R.id.createNotificationButton)
        val clearNotificationButton: Button = findViewById(R.id.clearNotificationButton)

        //Set click listener
        setButton.setOnClickListener {
            alarmManager?.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 5_000,
                pendingIntent
            )
        }
        cancelButton.setOnClickListener {
            alarmManager?.cancel(pendingIntent)
        }
        createNotificationButton.setOnClickListener {
            generateNotification(this, "notification", "message", 5)
        }
        clearNotificationButton.setOnClickListener {
            cancelNotification(this, 5)
        }
    }

//    //for API >= 26 (Android 8.0 - Oreo), create an actual channel with the provided ID
//    fun createNotificationChannel(context: Context, channelId: String="SI444 channel") {
//
//        // Create the NotificationChannel, but only on API 26+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//            val name = "SI444 channel "
//            val descriptionText = "channel for SI444 notifications"
//
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance)
//            channel.description = descriptionText
//
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            val notificationManager: NotificationManager =
//                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }
}