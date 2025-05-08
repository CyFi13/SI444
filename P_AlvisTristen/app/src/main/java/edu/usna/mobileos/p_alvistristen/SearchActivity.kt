package edu.usna.mobileos.p_alvistristen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Menu
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
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import java.io.ObjectOutputStream
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
    var selectedRoutine: Routine? = null
    var mode: String = "saved"  // used to track which set of routines are displayed

    val SAVED_ROUTINES_FILE: String = "saved_routines"

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
            if(selectedRoutine != null) {
                Log.i("SI444", "STARTING A WORKOUT")
                val workoutActivityIntent = Intent(this, WorkoutActivity::class.java)
                workoutActivityIntent.putExtra("routine", selectedRoutine)
                startActivity(workoutActivityIntent)
                finish()
            }
            else {
                Log.i("SI444", "ERROR, NO ROUTINE SELECTED")
                Toast.makeText(this,"PLEASE CHOOSE A ROUTINE", Toast.LENGTH_LONG)
                    .show()
            }
        }

        // set up text view
        selectTextView = findViewById(R.id.selected_routine_viewer)
        routineListTextView = findViewById(R.id.routine_set)
        // get intent data
        savedRoutines = intent.getSerializableExtra("saved_routines")
                as ArrayList<Routine>
        suggestedRoutines = intent.getSerializableExtra("suggested_routines")
                as ArrayList<Routine>
        // setup searchpage recyclerView
        recyclerView = findViewById(R.id.saved_routines)

        adapter1 = SearchpageAdapter(savedRoutines)
        adapter2 = SearchpageAdapter(suggestedRoutines)
        recyclerView.adapter = adapter1

        // swipe feature
        var touchlistener = MyTouchListener()
        routineListTextView.setOnTouchListener(touchlistener)
    }

    override fun onDestroy() {
        saveRoutineListToFile(SAVED_ROUTINES_FILE, savedRoutines)
        super.onDestroy()
    }

    override fun onStop() {
        saveRoutineListToFile(SAVED_ROUTINES_FILE, savedRoutines)
        super.onStop()
    }

    override fun onPause() {
        saveRoutineListToFile(SAVED_ROUTINES_FILE, savedRoutines)
        super.onPause()
    }

    /* Inflates the settings options menu on startup */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.go_back_menu -> {
                // return back to main menu
                val backIntent = Intent(this, MainActivity::class.java)
                startActivity(backIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
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
                selectedRoutine = routine
                selectTextView.text = routine.title
            }
            R.id.routine_details -> {
                val dialog = RoutineDetailsDialog(routine)
                dialog.show(supportFragmentManager, "RoutineDetailsDialog")
            }
            R.id.delete_routine -> {
                Log.i("SI444", "You are trying to delete routine in mode $mode")
                if(mode == "saved") {
                    savedRoutines.remove(routine)
                    adapter1 = SearchpageAdapter(savedRoutines)
                    recyclerView.adapter = adapter1
                }
                else if(mode == "discover" || mode == "filtered_discover") {
                    Toast.makeText(this,"CANNOT DELETE DISCOVERABLE ROUTINES",
                        Toast.LENGTH_LONG)
                        .show()
                }
                else {  // mode is filtered_saved
                    filteredRoutines.remove(routine)
                    recyclerView.adapter = adapter3
                    savedRoutines.remove(routine)
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    fun filter(text: String?) {
        Log.i("SI444", "You are trying to filter the string $text in mode $mode")
        val filteredlist = ArrayList<Routine>()
        val list: ArrayList<Routine>

        if(mode == "saved" || mode == "filtered_saved")
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
        Log.i("SI444", "there are ${filteredlist.size} items found")
        Log.i("SI444", "savedRoutine size is ${savedRoutines.size} and " +
                "suggestedRoutine size is ${suggestedRoutines.size}")
        if (filteredlist.isNotEmpty() &&
            (((mode == "saved" || mode == "filtered_saved") && filteredlist.size != savedRoutines.size) ||
            ((mode == "discover" || mode == "filtered_discover") && filteredlist.size != suggestedRoutines.size))) {
            adapter3 = SearchpageAdapter(filteredlist)
            recyclerView.adapter = adapter3
            filteredRoutines = filteredlist
            if (mode == "saved" || mode == "filtered_saved")
                mode = "filtered_saved"
            else
                mode = "filtered_discover"
        }
        else {  // if filter is empty/cleared
            Log.i("SI444", "TEST")
            if(mode == "saved" || mode == "filtered_saved") {
                mode = "saved"
                recyclerView.adapter = adapter1
            }
            else {
                mode = "discover"
                recyclerView.adapter = adapter2
            }
        }
    }

    fun saveRoutineListToFile(filename: String, obj: ArrayList<Routine>) {
        ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
            it.writeObject(obj)
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
                    if(mode == "saved" || mode == "filtered_saved") {   // only go right if in saved
                        routineListTextView.text = "Find New Routine"
                        recyclerView.adapter = adapter2
                        if(mode == "saved")
                            mode = "discover"
                        else {
                            mode = "filtered_discover"
                            filter(searchView.query.toString())
                            recyclerView.adapter = adapter3
                        }
                    }
                }
                else {  // left swipe
                    Log.i("SI444", "LEFT SWIPE")
                    if(mode == "discover" || mode == "filtered_discover") {
                        routineListTextView .text = "Saved Routines"
                        recyclerView.adapter = adapter1
                        if(mode == "discover")
                            mode = "saved"
                        else {
                            mode = "filtered_saved"
                            filter(searchView.query.toString())
                            recyclerView.adapter = adapter3
                        }
                    }
                }
                Log.i("SI444", "You are now in mode $mode")
            }
            return true
        }
    }

    class RoutineDetailsDialog(val item: Routine): DialogFragment(),
        DialogInterface.OnClickListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(activity)

            builder.setTitle(item.title)
            builder.setMessage(item.description)
            builder.setNegativeButton("Close", this)

            return builder.create()
        }

        override fun onClick(dialog: DialogInterface?, id: Int) {
            var buttonName = ""
            when(id) {
                Dialog.BUTTON_NEGATIVE -> { // CANCEL button
                    buttonName = "negative"
                    // CANCEL: CLOSE AND DO NOTHING
                }
            }
            Log.i("SI444", "You clicked $buttonName button")
        }
    }
}
