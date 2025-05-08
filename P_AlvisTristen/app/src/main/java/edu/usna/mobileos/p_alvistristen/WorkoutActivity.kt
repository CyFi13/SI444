package edu.usna.mobileos.p_alvistristen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibratorManager
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class WorkoutActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var layout: LinearLayout
    lateinit var routine: Routine
    lateinit var timerText: EditText
    var timer: Int = 0  // timer in seconds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        // set up main viewing window
        layout = findViewById(R.id.routine_layout)

        routine = intent.getSerializableExtra("routine") as Routine
        Log.i("SI444", "Routine being used is $routine")

        // create scrollview with instructions
        buildInstructions()

        timerText = findViewById(R.id.timer)

        val button: Button = findViewById(R.id.timer_button)
        button.setOnClickListener(this)
    }

    /* Inflates the settings options menu on startup */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.go_back_menu -> {
                val backIntent = Intent(this, MainActivity::class.java)
                startActivity(backIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buildInstructions() {
        for(set in routine.routine) {
            val textView = TextView(this)
            textView.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 100)
            textView.text = "${set.exercise.title} (${set.repCount} sets)\n\t${set.exercise.description}"
            layout.addView(textView)
       }
    }

    override fun onClick(v: View?) {
        var timer_val = timerText.text.toString()
        if(timer_val != "") {
            timer = timer_val.toInt()
        }
        else
            timer = 0
        timerText.clearFocus()
        if(timer != 0){
            val thread = object : Thread() {
                override fun run() {
                    for(i in timer downTo 0) {
                        runOnUiThread{ timerText.setText("$i") }
                        sleep(1000)
                    }
                }
            }
            thread.start()
        }
    }
}