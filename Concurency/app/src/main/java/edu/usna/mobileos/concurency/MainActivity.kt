package edu.usna.mobileos.concurency

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var outputText: TextView
    private val TAG = "SI444"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        outputText = findViewById(R.id.outputText)
    }

    fun runTask(v: View?) {

        // VERSION 0
        // Running a task on the UI Thread
        Log.i(TAG, "Blocking Task Started")
        try {
            // simulate a long-running task
            Thread.sleep(5000)
            outputText.text = "Task Complete"
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        Log.i(TAG, "Blocking Task Finished")
    }
}