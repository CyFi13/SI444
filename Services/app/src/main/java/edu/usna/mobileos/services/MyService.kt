package edu.usna.mobileos.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast

class MyService : Service() {
    var isRunning : Boolean = true
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Toast.makeText(this, "MyService started", Toast.LENGTH_LONG).show()
        doWork()
        return START_STICKY
    }

    override fun onDestroy() {
        isRunning = false
        super.onDestroy()
        Toast.makeText(this, "MyService destroyed", Toast.LENGTH_LONG).show()

    }

    fun doWork(){
        val thread = object : Thread() {
            override fun run() {
                try {
                    //create an intent
                    val broadcastIntent = Intent()
                    //set user-defined action
                    broadcastIntent.action = "WORK_COMPLETE_ACTION"
                    // find prime numbers
                    var num_tested = 1
                    var i = 2
                    while(isRunning) {
                        var prime = true
                        for (x in 2..i-1) {
                            if(i % x == 0) {
                                prime = false
                            }
                        }
                        if(prime){
                            broadcastIntent.putExtra("num_tested", num_tested)
                            broadcastIntent.putExtra("num", i)

                            //broadcast the intent
                            baseContext.sendBroadcast(broadcastIntent)

                        }

                        i += 1
                        num_tested += 1
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        thread.start()
    }
}