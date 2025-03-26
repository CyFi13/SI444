package edu.usna.mobileos.concurency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var outputText: TextView
    private val TAG = "SI444"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputText = findViewById(R.id.outputText)
    }

    fun runTask(v: View?) {
            // VERSION 2a
            // Running a task in a separate thread created using an implicit Runnable
            val thread = Thread {
                Log.i(TAG, "Runnable Started")
                try {
                    for (i in 0..4) {

                        runOnUiThread { outputText.text = "Current Count = $i" }
                        Thread.sleep(2000) //pause for 2 seconds
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Log.i(TAG, "Runnable Finished")
            }
            thread.start()
    }

    fun generateContent(view: View?) {
        outputText.text = "Random number: \n ${Random.nextInt()}"
    }
}