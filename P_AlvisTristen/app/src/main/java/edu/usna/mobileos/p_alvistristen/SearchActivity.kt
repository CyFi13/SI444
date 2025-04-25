package edu.usna.mobileos.p_alvistristen

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.Button
import android.widget.ScrollView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SearchpageAdapter
    lateinit var textView: TextView
    lateinit var savedRoutines: ArrayList<Routine>
    lateinit var suggestedRoutines: ArrayList<Routine>
    lateinit var start_button: Button
    lateinit var searchView: SearchView
    lateinit var mDectector: GestureDetectorCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // setup search view
        // information found at developer.android.com
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = findViewById(R.id.search_bar)

        // set up go button
        start_button = findViewById(R.id.start_workout_button)
        start_button.setOnClickListener {
            val workoutActivityIntent = Intent(this, WorkoutActivity::class.java)
            startActivity(workoutActivityIntent)
        }

        // set up text view
        textView = findViewById(R.id.selected_routine_viewer)

        // setup searchpage recyclerView
        recyclerView = findViewById(R.id.saved_routines)
        savedRoutines = ArrayList()
        for(i in (0..30)) { // testing recyclerView
            var set = ArrayList<Set>()
            var routine: Routine = Routine("test" + i, "", set)
            savedRoutines.add(routine)
        }
        adapter = SearchpageAdapter(savedRoutines)
        recyclerView.adapter = adapter

        mDectector = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val routine = savedRoutines[position]

        when(item.itemId) {
            R.id.select_routine -> {
                textView.text = routine.title
            }
            R.id.routine_details -> {

            }
            R.id.delete_routine -> {

            }
        }
        return super.onContextItemSelected(item)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        mDectector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }


    private class MyGestureListener:GestureDetector.SimpleOnGestureListener() {
        override fun onDown(event: MotionEvent): Boolean {
            Log.d("SI444", "onDown: $event")
            return true
        }
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d("SI444", "onFling: $e1 $e2")
            return true
        }
    }
}