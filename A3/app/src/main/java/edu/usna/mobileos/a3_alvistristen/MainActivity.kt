package edu.usna.mobileos.a3_alvistristen
/**
 * Filename: MainActivity.kt
 * Author: MIDN Tristen Alvis (260102)
 * Date: 6Apr2025
 */
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnMultiChoiceClickListener
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

/**
 * Main Activity for the app. Allows users to create, edit, and delete ToDos as a task manager.
 */
class MainActivity : AppCompatActivity(), MyDialogReturnInterface {
    lateinit var prefs: SharedPreferences
    lateinit var todoView: RecyclerView
    var todos: ArrayList<ToDo> = ArrayList<ToDo>()  // stores todos
    val requestCode1 = 260102   // request code for AddToDosActivity
    val requestCode2 = 260103   // request code for EditToDosActivity
    val SAVE_FILE = "data"  // save file

    /* onCreate method for MainActivity */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get saved todos from savefile when created
        if (getToDosFromFile(SAVE_FILE) != null) {
            var saved_todos: ArrayList<ToDo> = getToDosFromFile(SAVE_FILE) as ArrayList<ToDo>
            todos.addAll(saved_todos)
        }

        // setup recycler view to display todo items
        todoView = findViewById(R.id.todo_list)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val adapter = ToDoAdapter(todos)
        todoView.adapter = adapter
    }

    /* creates options menu for MainActivity */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    /* Options menu has 2 options: add and delete todos */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add -> {
                val addToDo = Intent(this, AddToDosActivity::class.java)
                startActivityForResult(addToDo, requestCode1)
                true
            }
            R.id.delete -> {
                val dialog = DeleteListDialog(this, todos)
                dialog.show(supportFragmentManager, "DeleteListDialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* allows for transferring data back from dialog to MainActivity */
    override fun onDialogPositiveClick(toDoList: ArrayList<ToDo>) {
        Log.i("SI444", toDoList.toString())
        todos = toDoList

        val adapter = ToDoAdapter(todos)
        todoView.adapter = adapter
    }

    /* onActivityResult for MainActivity. Makes changes based on data from EditToDos and AddToDosActivity */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == this.requestCode1) {
            if (resultCode == RESULT_OK) {
                val todo: Serializable? = data?.getSerializableExtra("todos")
                todos.add(todo as ToDo)
                Log.i(
                    "SI444",
                    "add todo (" + todo.title + " " + todo.description + " " + todo.dateCreated + ") successful"
                )
            }
        }
        if (requestCode == this.requestCode2) {
            if (resultCode == RESULT_OK) {
                val position: Int? = data?.getIntExtra("position", -1)
                val toDo: Serializable? = data?.getSerializableExtra("todo")
                Log.i("SI444", "position " + position + "toDo: " + toDo)
                todos[position!!] = toDo as ToDo

                val adapter = ToDoAdapter(todos)
                todoView.adapter = adapter
            }
        }
    }

    /* Save todo data when app is paused */
    override fun onPause() {
        super.onPause()
        saveToDosToFile(SAVE_FILE, todos)
    }

    /* Save todo data when app is stopped */
    override fun onStop() {
        super.onStop()
        saveToDosToFile(SAVE_FILE, todos)
    }

    /* writes todos to save file */
    fun saveToDosToFile(filename: String, ToDoList: ArrayList<ToDo>) {
        try {
            ObjectOutputStream(openFileOutput(filename, MODE_PRIVATE)).use {
                it.writeObject(ToDoList)
                Log.i("SI444", "successfully saved to file")
            }
        } catch (e: IOException) {
            Log.e("SI444", "IOException writing file $filename")
        }
    }

    /* reads and gets todos from save file */
    fun getToDosFromFile(filename: String): Any? {
        try {
            ObjectInputStream(openFileInput(filename)).use {
                return it.readObject()
            }
        } catch (e: IOException) {
            Log.e("SI444", "IOException writing file $filename")
            return null
        }
    }

    /* provides context menu options for each todo item (show, edit, delete) */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = item.groupId
        val toDoItem = todos[position]

        when (item.itemId) {
            R.id.show_detail -> {
                val dialog = SimpleAlertDialog(todos[position])
                dialog.show(supportFragmentManager, "SimpleAlertDialog")
            }

            R.id.edit_detail -> {
                val editToDoIntent = Intent(this, EditToDosActivity::class.java)
                editToDoIntent.putExtra("ToDoItem", toDoItem)
                editToDoIntent.putExtra("ToDoIndex", position)
                startActivityForResult(editToDoIntent, requestCode2)
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

/* allows for data transfer from Dialog back to MainActivity */
interface MyDialogReturnInterface {
    fun onDialogPositiveClick(toDoList: ArrayList<ToDo>)
}

/* Dialog that allows users to delete multiple todo items at once (checkbox) */
class DeleteListDialog(var myInterface: MyDialogReturnInterface, val toDoList: ArrayList<ToDo>): DialogFragment(),
    OnMultiChoiceClickListener, DialogInterface.OnClickListener {
    lateinit var choices: ArrayList<String>
    var deleteList: ArrayList<ToDo> = ArrayList()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        choices = ArrayList()
        for (item in toDoList) {
            choices.add(item.title)
        }

        builder.setTitle("Delete ToDos")
            .setMultiChoiceItems(choices.toTypedArray(), null, this)
            .setPositiveButton("OK", this)
            .setNegativeButton("Cancel", this)
        return builder.create()
    }

    // Single Button Listener
    override fun onClick(dialog: DialogInterface, id: Int) {
        var buttonName = ""
        when (id) {
            Dialog.BUTTON_POSITIVE -> {
                buttonName = "Positive"
                for (item in deleteList) {
                    toDoList.remove(item)
                }
                myInterface.onDialogPositiveClick(toDoList)
            }
            Dialog.BUTTON_NEGATIVE -> {
                buttonName = "Negative"
            }
        }
        Log.i("SI444", "You clicked the $buttonName button")
    }

    // MultiChoice Item Listener
    override fun onClick(dialog: DialogInterface?, itemId: Int, isChecked: Boolean) {
        var buttonName = ""
        if(isChecked) {
            buttonName = "CHECK"
            deleteList.add(toDoList[itemId])
        }
        else {
            buttonName = "UNCHECK"
            deleteList.remove(toDoList[itemId])
        }
        Log.i("SI444", "You clicked the $buttonName button")
    }
}

/* Dialog that shows users more information about todo item */
class SimpleAlertDialog(val toDo: ToDo): DialogFragment(),DialogInterface.OnClickListener {
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


