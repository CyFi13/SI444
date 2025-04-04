package edu.usna.mobileos.alarmexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.startService(Intent(context, MyAlarmService::class.java))

        val launchIntent = Intent()
        launchIntent.setClassName("edu.usna.mobileos.playsound2", "edu.usna.mobileos.playsound2.MainActivity")
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context?.startActivity(launchIntent)
    }
}