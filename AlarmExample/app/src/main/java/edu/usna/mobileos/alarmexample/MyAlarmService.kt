package edu.usna.mobileos.alarmexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlin.concurrent.thread

class MyAlarmService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        thread {
            Log.d("AlarmExample", "Service started by alarm")
        }
        Log.i("SI444", "Service started by alarm!!")
        generateNotification(baseContext,
            "Service Launched Notification",
            "This notification launched by your service",
            1775)

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}