package edu.usna.mobileos.a3_alvistristen

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var prefs: SharedPreferences
    lateinit var todoView: RecyclerView
    var todos: ArrayList<ToDo> = ArrayList<ToDo>()
    val requestCode = 260102
    val SAVE_FILE = "filename1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get saved todos from savefile
        if(getToDosFromFile(SAVE_FILE) != null) {
            var saved_todos: ArrayList<ToDo> = getToDosFromFile(SAVE_FILE) as ArrayList<ToDo>
            todos.addAll(saved_todos)
        }

        todoView = findViewById(R.id.todo_list)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        val adapter = ToDoAdapter(todos)
        todoView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val addToDo = Intent(this, AddToDosActivity::class.java)
                startActivityForResult(addToDo, requestCode)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == this.requestCode) {
            if(resultCode == RESULT_OK) {
                var todo: Serializable? = data?.getSerializableExtra("todos")
                todos.add(todo as ToDo)
                Log.i("SI444", "add todo ("  + todo.title + " " + todo.description + " " + todo.dateCreated + ") successful")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveToDosToFile(SAVE_FILE, todos)
    }

    override fun onStop() {
        super.onStop()
        saveToDosToFile(SAVE_FILE, todos)
    }

    fun saveToDosToFile(filename: String, ToDoList: ArrayList<ToDo>) {
        try {
            ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
                it.writeObject(ToDoList)
                Log.i("SI444", "successfully saved to file")
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val toDoItem = todos[position]

        when(item.itemId) {
            R.id.show_detail -> {
                val dialog = SimpleAlertDialog(todos[position])
                dialog.show(supportFragmentManager, "SimpleAlertDialog")
            }
            R.id.edit_detail -> {

            }
            R.id.delete_item -> {
                todos.removeAt(position)
                Log.i("SI444", "item at pos" + position.toString() + "removed successfully")
                saveToDosToFile(SAVE_FILE, todos)

                // refresh adapter to no longer display deleted items
                val adapter = ToDoAdapter(todos)
                todoView.adapter = adapter
            }
        }
        return super.onContextItemSelected(item)
    }
}

class SimpleAlertDialog(val toDo: ToDo): DialogFragment(), DialogInterface.OnClickListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle(toDo.title)
        builder.setMessage("Description: " + toDo.description + "\n\n" + "Created On: " + toDo.dateCreated)
        builder.setPositiveButton("Ok", this)

        return builder.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        var buttonName = ""
        when (id) {
            Dialog.BUTTON_POSITIVE -> buttonName = "Positive"
        }
        Log.d("SI444", "You clicked the $buttonName button")
    }
}

