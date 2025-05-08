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
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity(), RoutineDialogReturnInterface {
    lateinit var routineListView: RecyclerView
    lateinit var routineListAdapter: HomepageAdapter
    lateinit var suggestedRoutineList: ArrayList<Routine>  // TODO: find a way to get these from online
    var savedRoutineList: ArrayList<Routine> = ArrayList()
    val SETTINGS_FILE: String = ""  // TODO: Settings data stored in shared preferences
    val STORED_ROUTINES_FILE: String = "stored_routines" // TODO: maybe be able to load workouts from online into this file
    val SAVED_ROUTINES_FILE: String = "saved_routines"

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup homepage recyclerView
        routineListView = findViewById(R.id.homepage_recyclerView)
        suggestedRoutineList = java.util.ArrayList()
        for(i in (0..30)) { // testing
            val set = ArrayList<Set>()
            for(j in 0..10) {
                val exercise = Exercise("test$j", "description$j")
                val s = Set(exercise, j)
                set.add(s)
            }
            val routine = Routine("test" + i, "test", set)
            suggestedRoutineList.add(routine)
        }
        routineListAdapter = HomepageAdapter(suggestedRoutineList)
        routineListView.adapter = routineListAdapter

        // initialize saved routines array
        savedRoutineList = getRoutineListFromFile(SAVED_ROUTINES_FILE)

        // setup workout button
        val button: Button = findViewById(R.id.workout_button)

        button.setOnClickListener {
            val searchActivityIntent = Intent(this, SearchActivity::class.java)
            searchActivityIntent.putExtra("saved_routines", savedRoutineList)
            searchActivityIntent.putExtra("suggested_routines", suggestedRoutineList)
            startActivity(searchActivityIntent)
            finish()
        }

    }

    override fun onStop() {
        saveRoutineListToFile(SAVED_ROUTINES_FILE, savedRoutineList)
        super.onStop()
    }

    override fun onDestroy() {
        saveRoutineListToFile(SAVED_ROUTINES_FILE, savedRoutineList)
        super.onDestroy()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        Log.i("SI444", "item pos $position")
        val routine = suggestedRoutineList[position]
        Log.i("SI444", "routine value $routine")

        when (item.itemId) {
            R.id.routine_details -> {
                val dialog = RoutineDetailsDialog(routine, this)
                dialog.show(supportFragmentManager, "RoutineDetailsDialog")
            }
            R.id.save_routine -> {
                savedRoutineList.add(routine)
            }
        }
        return super.onContextItemSelected(item)
    }
    fun saveRoutineListToFile(filename: String, obj: ArrayList<Routine>) {
        ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
            it.writeObject(obj)
        }
    }

    fun getRoutineListFromFile(filename: String): ArrayList<Routine> {
        try {
            ObjectInputStream(openFileInput(filename)).use {
                return it.readObject() as ArrayList<Routine>
            }
        }
        catch(e: IOException) {
            return ArrayList<Routine>()
        }

    }

    /* Class for dialog window that appears when users click on items in homepage recycler */
    class RoutineDetailsDialog(val item: Routine, var myInterface: RoutineDialogReturnInterface):
        DialogFragment(), DialogInterface.OnClickListener {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(activity)

            builder.setTitle(item.title)
            builder.setMessage(item.description)
            builder.setPositiveButton("Save", this)
            builder.setNegativeButton("Cancel", this)

            return builder.create()
        }

        override fun onClick(dialog: DialogInterface?, id: Int) {
            var buttonName = ""
            when(id) {
                Dialog.BUTTON_POSITIVE -> { // SAVE button
                    buttonName = "positive"
                    myInterface.onDialogPositiveClick(item) // pass data back to main
                }
                Dialog.BUTTON_NEGATIVE -> { // CANCEL button
                    buttonName = "negative"
                    // CANCEL: CLOSE AND DO NOTHING
                }
            }
            Log.i("SI444", "You clicked $buttonName button")
        }
    }

    override fun onDialogPositiveClick(item: Routine) {
        savedRoutineList.add(item)
    }
}
interface RoutineDialogReturnInterface {
    fun onDialogPositiveClick(item: Routine)
}

