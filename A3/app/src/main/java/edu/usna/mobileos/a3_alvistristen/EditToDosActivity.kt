package edu.usna.mobileos.a3_alvistristen
/**
 * Filename: EditToDosActivity.kt
 * Author: MIDN Tristen Alvis (260102)
 * Date: 6Apr2025
 */
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.util.Date

/**
 * Activity that allows users to edit todo items. passes new information back to main activity
 */
class EditToDosActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var title: EditText
    lateinit var description: EditText
    lateinit var date: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_dos)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)

        val button: Button = findViewById(R.id.edit_button)
        button.setOnClickListener(this)

        val toDo: ToDo  = intent.extras?.getSerializable("ToDoItem") as ToDo

        date = toDo.dateCreated
        title.setText(toDo.title)
        description.setText(toDo.description)
    }

    override fun onClick(v: View?) {
        val position = intent.extras?.getInt("ToDoIndex")
        val newTitle: String = title.text.toString()
        val newDescription: String = description.text.toString()
        Log.i("SI444", "position is " + position + " title is "
        + newTitle + " description is " + newDescription)
        val toDo = ToDo(newTitle, newDescription, date)

        Log.i("SI444", "new todo is: " + toDo)
        val resultIntent = Intent()
        resultIntent.putExtra("todo", toDo)
        resultIntent.putExtra("position", position)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}