package edu.usna.mobileos.a2_alvistristen

import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView : WebView = findViewById(R.id.url_page)
        val editText : EditText = findViewById(R.id.url_text)
        webView.webViewClient = USNAWebViewClient(editText)
        val goButton: Button = findViewById(R.id.url_button)
        goButton.setOnClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.forward -> {
                Snackbar.make(findViewById(android.R.id.content), "forward pressed",
                    Snackbar.LENGTH_LONG).show()
                true
            }
            R.id.back -> {
                Snackbar.make(findViewById(android.R.id.content), "back pressed",
                    Snackbar.LENGTH_LONG).show()
                true
            }
            R.id.bookmarks -> {
                Snackbar.make(findViewById(android.R.id.content), "bookmarks pressed",
                    Snackbar.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onClick(v: View?) {
        val webView : WebView = findViewById(R.id.url_page)
        val editText : EditText = findViewById(R.id.url_text)
        val url: String = editText.text.toString()
        when(v?.id){
            R.id.url_button -> {
                webView.loadUrl(url)
                Log.i("URL", url)
            }
        }
    }

    //extend WebViewClient class
    private class USNAWebViewClient(editText: EditText?) :  WebViewClient() {
        val editText : EditText? = editText
        /* these errors might be sent due to missing CA */
        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            Log.e("SI444", "SSL error: " + error.toString())
            //not recommended, but needed due to missing CA on AVDs
            handler?.proceed()
            //super.onReceivedSslError(view, handler, error)
        }

        /* changes url text in EditText View when the user navigates to a new webpage */
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.i("SI444", "page started")

            editText?.setText(url)
        }
    }
}

