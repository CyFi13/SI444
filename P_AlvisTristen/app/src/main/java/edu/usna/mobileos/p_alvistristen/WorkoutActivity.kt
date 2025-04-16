package edu.usna.mobileos.p_alvistristen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView

class WorkoutActivity : AppCompatActivity() {
    lateinit var scrollView: ScrollView
    lateinit var routine: Routine
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        // set up main viewing window
        scrollView = findViewById(R.id.routine_instructions_view)

        // TODO: recieve intent containing routine data from searchActivity and place that into list
        // test
        val sets = ArrayList<Set>()
        for(i in (0..30)) { // testing recyclerView
            var set: Set = Set("test1", )

            sets.add(set)
        }
        var routine: Routine = Routine("test", "", sets)

        // TODO: build up the scroll view with linear layouts to display the workout instructions
        buildInstructions()
    }

    private fun buildInstructions() {
        for(exercise in routineList) {
            val layout = LinearLayout(this)
            val textView = TextView(this)

            textView.text = exercise.description

            layout.addView(textView)

            val layout_parameters = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )

            layout.layoutParams = layout_parameters
        }
    }
}