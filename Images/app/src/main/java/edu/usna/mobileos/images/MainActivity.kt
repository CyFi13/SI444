package edu.usna.mobileos.images

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream

class MainActivity : AppCompatActivity(), View.OnClickListener{
    lateinit var imageView : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setImageResource(R.drawable.pic2)

        var inStream : InputStream? = null
        try {
            inStream = assets.open("severn.jpg")
        }
        catch (e: IOException){
            Log.e("SI444", "could not open asset severn.jpg")
        }
        imageView.setImageResource(R.drawable.pic2)

        val imageButton : ImageView = findViewById(R.id.imageView1)
        val imageButton2 : ImageView = findViewById(R.id.imageView2)
        imageButton.setOnClickListener(this)
        imageButton2.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v is ImageView) {
            val myImageView: ImageView = v
            val clickedDrawable = myImageView.drawable
            imageView.setImageDrawable(clickedDrawable)
        }
    }
}