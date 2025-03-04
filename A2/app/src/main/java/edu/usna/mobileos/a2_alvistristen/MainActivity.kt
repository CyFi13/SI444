package edu.usna.mobileos.a2_alvistristen

/* Filename: MainActivity.kt
 * Author: MIDN Tristen Alvis (260102)
 * Assignment #2: I completed all 4 phases and did the custom app icon for extra credit
 */

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
/* MainActivity
* Author: MIDN Tristen Alvis, m260102@usna.edu
* Date: 3 March 2025
* Description: the "main" activity for my poor man's web browser app. It is the activity that is
* first launched on startup. It contains an EditText view for reading/writing URLs, a GO button,
* a WebView to display web contents, and an options and context menu for navigating different parts
* of the app.
* */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var webView: WebView
    lateinit var editText: EditText
    var bookmarklist: ArrayList<String> = ArrayList<String>()   // stores list of bookmarks that user adds with menu option
    var requestCode = 260102

    /**
     * This method initializes the necessary views when MainActivity is launched.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.url_page)
        editText = findViewById(R.id.url_text)
        webView.webViewClient = USNAWebViewClient()

        val goButton: Button = findViewById(R.id.url_button)
        goButton.setOnClickListener(this)

        registerForContextMenu(webView)
    }

    /**
     * This method inflates the options_menu for the app
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    /**
     * This method provides logic for each of the option menu items (forward, back, bookmarks).
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.forward -> {
                Snackbar.make(
                    findViewById(android.R.id.content), "forward pressed",
                    Snackbar.LENGTH_LONG
                ).show()

                if (webView.canGoForward())
                    webView.goForward()

                true
            }
            R.id.back -> {
                Snackbar.make(
                    findViewById(android.R.id.content), "back pressed",
                    Snackbar.LENGTH_LONG
                ).show()

                if(webView.canGoBack())
                    webView.goBack()

                true
            }
            R.id.bookmarks -> {
                Snackbar.make(
                    findViewById(android.R.id.content), "bookmarks pressed",
                    Snackbar.LENGTH_LONG
                ).show()

                // intent stuff to open recycler bookmarks view
                val bookmarksIntent = Intent(this, BookmarksActivity::class.java)
                bookmarksIntent.putStringArrayListExtra("bookmarks", bookmarklist)
                startActivityForResult(bookmarksIntent, requestCode)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * this method inflates the context menu for the app
     */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    /**
     * this method provides logic for the context menu option (add_to_bookmarks)
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_to_bookmarks -> {
                bookmarklist.add(webView.url.toString())
                Log.i("SI444", "bookmark added " + webView.url.toString())

                return true
            }

            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    /**
     * This method provides custom onClick logic for the GO button
     */
    override fun onClick(v: View?) {
        val url: String = editText.text.toString()
        when (v?.id) {
            R.id.url_button -> {
                webView.loadUrl(url)
                Log.i("URL", url)
            }
        }
    }

    /**
     * This method is used to receive data from the BookmarksActivity in order to search an
     * item in the MainActivity
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        Log.i("SI444", "onActivityResult opened")
        if (requestCode == this.requestCode) {
            Log.i("SI444", "request code is good")
            if (resultCode == RESULT_OK) {
                webView.loadUrl(intent?.getStringExtra("url").toString())
            }
        }
    }

    /** USNAWebViewClient
     * Author: MIDN Tristen Alvis, m260102@usna.edu
     * Date: 3 March 2025
     * Description: overwrites the WebViewClient() activity to give custom features to the app's
     * WebView
     */
    inner class USNAWebViewClient : WebViewClient() {
        /* these errors might be sent due to missing CA */
        @SuppressLint("WebViewClientOnReceivedSslError")
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

            editText.setText(url)
        }

    }
}


