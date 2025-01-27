package edu.usna.mobileos.lifecycledemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRestart() {
        super.onRestart()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onRestart()")
    }

    override fun onStart() {
        super.onStart()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onStart()")
    }

    override fun onResume() {
        super.onResume()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onResume()")
    }

    override fun onPause() {
        super.onPause()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onPause()")
    }

    override fun onStop() {
        super.onStop()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        val TAG: String = "SI444"
        Log.i(TAG, "Inside onDestroy()")
    }
}