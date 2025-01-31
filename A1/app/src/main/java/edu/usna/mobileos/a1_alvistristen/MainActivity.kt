package edu.usna.mobileos.a1_alvistristen

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // instance properties
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // portrait orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            textView = findViewById(R.id.portraitText)
            val buttonGrid = findViewById<GridLayout>(R.id.porButtonGrid)

            // iterate through buttons in grid and give them onclick listeners
            for(i in 0 until buttonGrid.childCount) {
                buttonGrid.getChildAt(i).setOnClickListener(this)
            }

            val button1: Button = findViewById(R.id.port_button1)
            button1.setOnClickListener(this)
        }
        // landscape orientation
        else{
            //
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.port_button1 -> {
                textView.text = "0"
            }
            R.id.port_button2 -> {
                val text: String = "-" + textView.text
                textView.text = text
            }
            R.id.port_button3 -> {
                val text: String = textView.append("%")
                textView.text = text
            }
            R.id.port_button4 -> {
                textView.text = "cat"
            }
            R.id.port_button5 -> {
                textView.text = "7"
            }
            R.id.port_button6 -> {
                textView.text = "7"
            }
            R.id.port_button7 -> {
                textView.text = "7"
            }
            R.id.port_button8 -> {
                textView.text = "7"
            }
            R.id.port_button9 -> {
                textView.text = "7"
            }
            R.id.port_button10 -> {
                textView.text = "7"
            }
            R.id.port_button11 -> {
                textView.text = "7"
            }
            R.id.port_button12 -> {
                textView.text = "7"
            }
            R.id.port_button13 -> {
                textView.text = "7"
            }
            R.id.port_button14 -> {
                textView.text = "7"
            }
            R.id.port_button15 -> {
                textView.text = "7"
            }
            R.id.port_button16 -> {
                textView.text = "7"
            }
            R.id.port_button17 -> {
                textView.text = "7"
            }
            R.id.port_button18 -> {
                textView.text = "7"
            }
            R.id.port_button19 -> {
                textView.text = "7"
            }

        }
    }

}