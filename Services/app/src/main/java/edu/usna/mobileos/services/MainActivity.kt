package edu.usna.mobileos.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var serviceIntent : Intent
    lateinit var intentFilter: IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        serviceIntent = Intent(baseContext, MyService::class.java)
        intentFilter = IntentFilter()
        intentFilter.addAction("WORK_COMPLETE_ACTION") //note the same action as broadcast by the Service


        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button1 -> {
                startService(serviceIntent)
                registerReceiver(intentReceiver, intentFilter)
            }
            R.id.button2 -> {
                stopService(serviceIntent)
                unregisterReceiver(intentReceiver)
            }
        }
    }

    private val intentReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var prime = intent?.getIntExtra("num", -1)
            var num_tested = intent?.getIntExtra("num_tested", -1)

            val text: TextView= findViewById(R.id.text)
            text.setText(prime.toString() + " is prime and " +
                    num_tested.toString() + " numbers have been tested so far")
        }
    }
}