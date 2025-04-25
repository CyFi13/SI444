package edu.usna.mobileos.p_alvistristen

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MenuItem
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.widget.Button
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class SearchActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter1: SearchpageAdapter
    lateinit var adapter2: SearchpageAdapter
    lateinit var adapter3: SearchpageAdapter
    lateinit var selectTextView: TextView
    lateinit var routineListTextView: TextView
    lateinit var savedRoutines: ArrayList<Routine>
    lateinit var suggestedRoutines: ArrayList<Routine>
    lateinit var filteredRoutines: ArrayList<Routine>
    lateinit var start_button: Button
    lateinit var searchView: SearchView

    var mode: String = "saved"  // used to track which set of routines are displayed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // setup search view
        // information found at developer.android.com
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = findViewById(R.id.search_bar)

        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return false
            }
        })

        // set up go button
        start_button = findViewById(R.id.start_workout_button)
        start_button.setOnClickListener {
            val workoutActivityIntent = Intent(this, WorkoutActivity::class.java)
            startActivity(workoutActivityIntent)
        }

        // set up text view
        selectTextView = findViewById(R.id.selected_routine_viewer)
        routineListTextView = findViewById(R.id.routine_set)

        // setup searchpage recyclerView
        recyclerView = findViewById(R.id.saved_routines)

        // for testing
        savedRoutines = ArrayList()
        for(i in (0..30)) { // testing recyclerView
            var set = ArrayList<Set>()
            var routine: Routine = Routine("testing saved" + i, "saved description + $i", set)
            savedRoutines.add(routine)
        }
        suggestedRoutines = ArrayList()
        for(i in (0..30)) { // testing recyclerView
            var set = ArrayList<Set>()
            var routine: Routine = Routine("testing suggested" + i, "suggested description + $i", set)
            suggestedRoutines.add(routine)
        }

        adapter1 = SearchpageAdapter(savedRoutines)
        adapter2 = SearchpageAdapter(suggestedRoutines)
        recyclerView.adapter = adapter1

        var touchlistener = MyTouchListener()
        routineListTextView.setOnTouchListener(touchlistener)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val routine: Routine

        if(mode=="saved")
            routine = savedRoutines[position]
        else if(mode == "discover")
            routine = suggestedRoutines[position]
        else
            routine = filteredRoutines[position]

        when(item.itemId) {
            R.id.select_routine -> {
                selectTextView.text = routine.title
            }
            R.id.routine_details -> {

            }
            R.id.delete_routine -> {

            }
        }
        return super.onContextItemSelected(item)
    }

    fun filter(text: String?) {
        val filteredlist = ArrayList<Routine>()
        val list: ArrayList<Routine>

        if(mode == "saved")
            list = savedRoutines
        else
            list = suggestedRoutines

        for(item in list) {
            if (text != null) {
                if(item.title.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }

        if(filteredlist.isNotEmpty()){
            adapter3 = SearchpageAdapter(filteredlist)
            recyclerView.adapter = adapter3
            filteredRoutines = filteredlist
            mode = "filtered"
        }
    }

    private inner class MyTouchListener:View.OnTouchListener {
        var x_initial: Double = 0.0
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if(event?.action == ACTION_DOWN) {
                x_initial = event.x.toDouble()
            }
            else if(event?.action == ACTION_UP) {
                var x_final = event.x.toDouble()

                var dx = x_final - x_initial

                if(dx > 0) {    // right swipe
                    Log.i("SI444", "RIGHT SWIPE")
                    routineListTextView.text = "Find New Routine"
                    mode = "discover"
                    recyclerView.adapter = adapter2
                }
                else {  // left swipe
                    Log.i("SI444", "LEFT SWIPE")
                    routineListTextView .text = "Saved Routines"
                    mode = "saved"
                    recyclerView.adapter = adapter1
                }
            }
            return true
        }
    }
}
