package edu.usna.mobileos.a3_alvistristen

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity(), ToDoListener {
    lateinit var prefs: SharedPreferences
    lateinit var todos: ArrayList<String>
    val FILENAME = "filename1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val todoView: RecyclerView = findViewById(R.id.todo_list)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        todos.add("test")

        val adapter = ToDoAdapter(todos, this)
        todoView.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    fun saveToDosToFile(filename: String, ToDoList: ArrayList<ToDo>) {
        try {
            ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
                it.writeObject(ToDoList)
            }
        }
        catch(e: IOException) {
            Log.e("SI444", "IOException writing file $filename")
        }
    }

    fun getToDosFromFile(filename: String): Any? {
        try{
            ObjectInputStream(openFileInput(filename)).use {
                return it.readObject()
            }
        }
        catch(e: IOException) {
            Log.e("SI444", "IOException writing file $filename")
            return null
        }
    }

    override fun onItemClick(todo: String) {
        TODO("Not yet implemented")
    }
}

