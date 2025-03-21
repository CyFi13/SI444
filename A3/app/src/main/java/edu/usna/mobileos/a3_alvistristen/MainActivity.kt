package edu.usna.mobileos.a3_alvistristen

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {
    lateinit var prefs: SharedPreferences
    val FILENAME = "filename1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    fun saveToDosToFile(ToDoList: ArrayList<ToDo>, filename: String) {
        openFileOutput(filename, MODE_PRIVATE).use {
            it.write(ToDoList.toByteArray())
        }
    }
}

