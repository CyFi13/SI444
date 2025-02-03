package edu.usna.mobileos.a1_alvistristen

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView

/**
 *
 * Author:      MIDN Tristen Alvis, m260102@usna.edu
 * Date:        3 Feb 2025
 * Description: Implementation of iPhone5s calculator user interface
 * in both portrait and landscape modes. Does not calculate anything
 * and only some buttons are programmed to print what they actually
 * represent.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    // instance properties
    lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // portrait orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            textView = findViewById(R.id.textview)
            val buttonGrid = findViewById<GridLayout>(R.id.button_grid)

            // iterate through buttons in grid and give them onclick listeners
            for(i in 0 until buttonGrid.childCount) {
                buttonGrid.getChildAt(i).setOnClickListener(this)
            }
        }
        // landscape orientation
        else {
            //
            textView = findViewById(R.id.textview)
            val buttonGrid = findViewById<GridLayout>(R.id.button_grid)

            for(i in 0 until buttonGrid.childCount) {
                buttonGrid.getChildAt(i).setOnClickListener(this)
            }
        }
    }

    /**
     * This method overrides onClick() to provide custom
     */
    override fun onClick(v: View?) {
        if(textView.text.length == 1 && textView.text.contains("0"))    // removes initial placeholder 0
            textView.text = ""

        when(v?.id) {
            R.id.ac -> {      // AC button: clears calculator
                textView.text = "0"
            }
            R.id.signchange-> {     // +/- button: changes sign
                if(textView.text.contains("-")) {
                    textView.text = textView.text.removePrefix("-")
                }
                else {
                    val text: String = "-" + textView.text
                    textView.text = text
                }
            }
            R.id.percent -> {   // percent button: adds a percent sign
                textView.text = textView.text.toString() + "%"
            }
            R.id.divide -> {    // division button: adds a division sign
                textView.text = textView.text.toString() + "\u00F7"
            }
            R.id.multiply -> {
                textView.text = textView.text.toString() + "\u00D7"
            }
            R.id.subtract -> {
                textView.text = textView.text.toString() + "-"
            }
            R.id.add -> {
                textView.text = textView.text.toString() + "+"
            }
            R.id.decimal -> {
                textView.text = textView.text.toString() + "."
            }
            R.id.equal -> {     // equal button: just resets screen in this case
                if(textView.text == "2026") {   // for fun function
                    textView.text = "( ͡° ͜ʖ ͡°)"
                }
                else if(textView.text == "12272003") {
                    textView.text = "ඞ ඞ ඞ ඞ ඞ"
                }
                else {
                    textView.text = "0"
                }
            }
            R.id.b0 -> {
                textView.text = textView.text.toString() + "0"
            }
            R.id.b1 -> {
                textView.text = textView.text.toString() + "1"
            }
            R.id.b2-> {
                textView.text = textView.text.toString() + "2"
            }
            R.id.b3 -> {
                textView.text = textView.text.toString() + "3"
            }
            R.id.b4 -> {
                textView.text = textView.text.toString() + "4"
            }
            R.id.b5 -> {
                textView.text = textView.text.toString() + "5"
            }
            R.id.b6 -> {
                textView.text = textView.text.toString() + "6"
            }
            R.id.b7 -> {
                textView.text = textView.text.toString() + "7"
            }
            R.id.b8 -> {
                textView.text = textView.text.toString() + "8"
            }
            R.id.b9 -> {
                textView.text = textView.text.toString() + "9"
            }
            R.id.left_parenthesis -> {
                textView.text = textView.text.toString() + "("
            }
            R.id.right_parenthesis -> {
                textView.text = textView.text.toString() + ")"
            }
            R.id.mc -> {
            }
            R.id.mplus -> {
            }
            R.id.mminus -> {
            }
            R.id.mr -> {
            }
            R.id.ln -> {
                textView.text = "ln(" + textView.text.toString() + ")"
            }
            R.id.factorial -> {
                textView.text = textView.text.toString() + "!"
            }
            R.id.sin -> {
                textView.text = "sin(" + textView.text.toString() + ")"
            }
            R.id.cos-> {
                textView.text = "cos(" + textView.text.toString() + ")"
            }
            R.id.tan -> {
                textView.text = "tan(" + textView.text.toString() + ")"
            }
            R.id.e -> {
                textView.text = textView.text.toString() + "e"
            }
            R.id.EE -> {
                textView.text = textView.text.toString() + "EE"
            }
            R.id.rad -> {
            }
            R.id.sinh -> {
                textView.text = "sinh(" + textView.text.toString() + ")"
            }
            R.id.cosh -> {
                textView.text = "cos(" + textView.text.toString() + ")"
            }
            R.id.tanh -> {
                textView.text = "tanh(" + textView.text.toString() + ")"
            }
            R.id.rand -> {
            }
            R.id.pi -> {
                textView.text = textView.text.toString() + "\u03C0"
            }
            else -> {
                textView.text = textView.text.toString() + "#"
            }
        }
    }

}