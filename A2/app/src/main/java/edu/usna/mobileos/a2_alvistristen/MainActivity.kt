package edu.usna.mobileos.a2_alvistristen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val webView : WebView = WebView(findViewById(R.id.url_page))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goButton: Button = findViewById(R.id.url_button)
        goButton.setOnClickListener(this)

        webView.loadUrl("https://www.apple.com/")
    }

    override fun onClick(v: View?) {
        val url: String = "https://apple.com"
        when(v?.id){
            R.id.url_button -> {
                webView.loadUrl(url)
            }
        }
    }
}