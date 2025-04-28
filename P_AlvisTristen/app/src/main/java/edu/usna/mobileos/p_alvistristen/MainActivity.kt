package edu.usna.mobileos.p_alvistristen

/**
 * Filename: MainActivity.kt
 * Author: MIDN Tristen Alvis (260102)
 * Description: Homepage file for SI444 project app
 */

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import java.io.ObjectOutputStream

val SETTINGS_FILE: String = ""  // TODO: Settings data stored in shared preferences
val STORED_ROUTINES_FILE: String = "stored_routines" // TODO: maybe be able to load workouts from online into this file
val SAVED_ROUTINES_FILE: String = "saved_routines"
class MainActivity : AppCompatActivity() {
    lateinit var routineListView: RecyclerView
    lateinit var routineListAdapter: HomepageAdapter
    lateinit var suggestedRoutineList: ArrayList<Routine>  // TODO: find a way to get these from online
    lateinit var savedRoutineList: ArrayList<Routine>

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup homepage recyclerView
        routineListView = findViewById(R.id.homepage_recyclerView)
        suggestedRoutineList = ArrayList()
        for(i in (0..30)) { // testing recyclerView
            var set = ArrayList<Set>()
            var routine: Routine = Routine("test" + i, "test", set)
            suggestedRoutineList.add(routine)
        }
        routineListAdapter = HomepageAdapter(suggestedRoutineList)
        routineListView.adapter = routineListAdapter

        // setup workout button
        val button: Button = findViewById(R.id.workout_button)

        button.setOnClickListener {
            val searchActivityIntent = Intent(this, SearchActivity::class.java)
            startActivity(searchActivityIntent)
        }

    }

    /* Inflates the settings options menu on startup */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        Log.i("SI444", "item pos $position")
        val routine = suggestedRoutineList[position]
        Log.i("SI444", "routine value $routine")

        when (item.itemId) {
            R.id.routine_details -> {
                Log.i("SI444", "TEST1")
                val dialog = RoutineDetailsDialog(routine)
                Log.i("SI444", "TEST2")
                dialog.show(supportFragmentManager, "RoutineDetailsDialog")
                Log.i("SI444", "TEST3")
            }
            R.id.save_routine -> {
                saveRoutineToFile(SAVED_ROUTINES_FILE, routine)
            }
        }
        return super.onContextItemSelected(item)
    }
    fun saveRoutineToFile(filename: String, obj: Routine) {
        ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
            it.writeObject(obj)
        }
    }

    /* Class for dialog window that appears when users click on items in homepage recycler */
    class RoutineDetailsDialog(val item: Routine): DialogFragment(), DialogInterface.OnClickListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            Log.i("SI444", "TEST4")
            val builder = AlertDialog.Builder(activity)

            builder.setTitle(item.title)
            builder.setMessage(item.description)
            builder.setPositiveButton("Save", this)
            builder.setNegativeButton("Cancel", this)

            return builder.create()
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(id) {
                Dialog.BUTTON_POSITIVE -> { // SAVE button
                    // MAKE AN INTENT FOR THIS
                }
                Dialog.BUTTON_NEGATIVE -> { // CANCEL button
                    // CANCEL: CLOSE AND DO NOTHING
                }
            }
        }
    }
}




