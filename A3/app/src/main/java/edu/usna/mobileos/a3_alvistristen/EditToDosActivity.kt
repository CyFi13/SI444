package edu.usna.mobileos.a3_alvistristen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.util.Date

class EditToDosActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var title: EditText
    lateinit var description: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_to_dos)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)

        val button: Button = findViewById(R.id.edit_button)
        button.setOnClickListener(this)

        val toDo: ToDo  = intent.extras?.getSerializable("ToDoItem") as ToDo

        title.text = toDo.title as Editable
        title.text = toDo.description as Editable
    }

    override fun onClick(v: View?) {
        val position = intent.extras?.getInt("ToDoIndex")
        finish()
    }
}