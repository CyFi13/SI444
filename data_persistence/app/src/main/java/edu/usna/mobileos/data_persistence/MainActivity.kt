package edu.usna.mobileos.data_persistence

import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        var colorString: String? = prefs.getString("color", "white")

        val view: View = findViewById(android.R.id.content)
        when(colorString) {
            "white" -> {
                view.setBackgroundColor(Color.WHITE)
            }

            "red" -> {
                view.setBackgroundColor(Color.RED)
            }

            "blue" -> {
                view.setBackgroundColor(Color.BLUE)
            }
        }

        val button1: Button = findViewById(R.id.red)
        val button2: Button = findViewById(R.id.blue)
        val button3: Button = findViewById(R.id.white)
        val button4: Button = findViewById(R.id.save)
        val button5: Button = findViewById(R.id.retrieve)
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val editor = prefs.edit()
        val background: View = findViewById(android.R.id.content)
        val editText: EditText = findViewById(R.id.input)
        when(v?.id) {
            R.id.red -> {
                background.setBackgroundColor(Color.RED)
                editor.putString("color", "red")
                Log.i("SI444", "RED BUTTON CLICKED")
            }
            R.id.blue -> {
                background.setBackgroundColor(Color.BLUE)
                editor.putString("color", "blue")
            }
            R.id.white -> {
                background.setBackgroundColor(Color.WHITE)
                editor.putString("color", "white")
            }
            R.id.save -> {
                var input: String = editText.text.toString()
                saveObjectToFile("data_file", input)
                Log.i("SI444", "SAVE BUTTON CLICKED")
            }
            R.id.retrieve -> {
                var s = getObjectFromFile("data_file")
                Log.i("SI444", s.toString())

                val v: TextView = findViewById(R.id.text)
                v.text = s.toString()
            }
        }
        editor.apply()
    }

    fun saveObjectToFile(fileName: String, obj: Any) {
        try {
            ObjectOutputStream(openFileOutput(fileName, MODE_PRIVATE)).use {
                it.writeObject(obj)
            }
        }
        catch (e: IOException){
            Log.e("SI444", "IOException writing file $fileName")
        }
    }
    fun getObjectFromFile(fileName: String): Any? {
        try{
            ObjectInputStream(openFileInput(fileName)).use{
                return it.readObject()
            }
        }
        catch (e: IOException){
            Log.e("SI444", "IOException reading file $fileName")
            return null
        }
    }
}