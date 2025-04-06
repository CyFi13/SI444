package edu.usna.mobileos.a3_alvistristen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import java.util.Calendar
import java.util.Date
/**
 * Filename: AddToDosActivity.kt
 * Author: MIDN Tristen Alvis (260102)
 * Date: 6Apr2025
 */

/**
 * activity that allows users to create a new todo item. New information is returned to MainActivity.
 */
class AddToDosActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var title: EditText
    lateinit var description: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_dos)

        title = findViewById(R.id.title)
        description = findViewById(R.id.description)

        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val resultIntent = Intent()
        val dateCreated = Date()
        val todo: ToDo = ToDo(title.text.toString(), description.text.toString(),
            dateCreated.toString())
        resultIntent.putExtra("todos", todo)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}