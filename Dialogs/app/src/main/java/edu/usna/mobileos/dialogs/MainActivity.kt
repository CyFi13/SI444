package edu.usna.mobileos.dialogs

import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = SimpleAlertDialog()
        dialog.show(supportFragmentManager, "SimpleAlertDialog")
    }

}

class SimpleAlertDialog : DialogFragment(), DialogInterface.OnClickListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setTitle("Alert Dialog Title")
            .setIcon(R.drawable.cs_logo)
            .setMessage("Hello. This is the message body.")
            .setPositiveButton("Pos", this)
            .setNeutralButton("Neu", this)
            .setNegativeButton("Neg", this)
            .setCancelable(false);

        return builder.create()
    }

    override fun onClick(dialogInterface: DialogInterface, id: Int) {
        var buttonName = ""
        when (id) {
            Dialog.BUTTON_NEGATIVE -> buttonName = "Negative"
            Dialog.BUTTON_NEUTRAL -> buttonName = "Neutral"
            Dialog.BUTTON_POSITIVE -> buttonName = "Positive"
        }
        Log.d("SI444", "You clicked the $buttonName button")
    }
}
