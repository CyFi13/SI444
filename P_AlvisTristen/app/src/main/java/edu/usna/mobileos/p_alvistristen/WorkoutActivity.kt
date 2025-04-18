package edu.usna.mobileos.p_alvistristen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class WorkoutActivity : AppCompatActivity() {
    lateinit var layout: LinearLayout
    lateinit var routine: Routine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)
        // set up main viewing window
        layout = findViewById(R.id.routine_layout)

        // TODO: recieve intent containing routine data from searchActivity and place that into list
        // test
        val sets = ArrayList<Set>()
        for(i in (0..50)) { // testing recyclerView
            var exercise: Exercise = Exercise("title", "description")
            var set: Set = Set(exercise, 10)
            sets.add(set)
        }
        routine = Routine("test", "", sets)

        // TODO: build up the scroll view with linear layouts to display the workout instructions
        buildInstructions()

    }

    private fun buildInstructions() {
        for(set in routine.routine) {
            val textView = TextView(this)
            textView.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, 100)
            textView.text = set.exercise.title
            layout.addView(textView)
       }
    }
}