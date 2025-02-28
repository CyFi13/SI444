package edu.usna.mobileos.a2_alvistristen

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

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var webView: WebView
    lateinit var editText: EditText
    var url_array : ArrayList<String> = ArrayList() // will be used to store urls visited
    var url_index : Int = 0
    var bookmarks : ArrayList<String> = ArrayList()
    var requestCode : Int = 26
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView= findViewById(R.id.url_page)
        editText = findViewById(R.id.url_text)
        webView.webViewClient = USNAWebViewClient()

        val goButton: Button = findViewById(R.id.url_button)
        goButton.setOnClickListener(this)

        registerForContextMenu(webView)
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

                webView.loadUrl(url_array.get(url_index - 1))
                true
            }
            R.id.back -> {
                Snackbar.make(findViewById(android.R.id.content), "back pressed",
                    Snackbar.LENGTH_LONG).show()

                webView.loadUrl(url_array.get(url_index + 1))
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

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_to_bookmarks -> {
                bookmarks.add(webView.url.toString())
                Log.i("SI444", "bookmark added")

                // intent stuff to open recycler bookmarks view
                val BookmarksIntent = Intent(this, BookmarksActivity::class.java)
                val extras = Bundle()
                extras.putStringArrayList("bookmarks", bookmarks)
                BookmarksIntent.putExtras(extras)
                startActivity(BookmarksIntent)
                return true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    override fun onClick(v: View?) {
        val url: String = editText.text.toString()
        when(v?.id){
            R.id.url_button -> {
                webView.loadUrl(url)
                Log.i("URL", url)
            }
        }
    }

    //extend WebViewClient class
    inner class USNAWebViewClient() :  WebViewClient() {
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

            editText.setText(url)
        }

        /* will save the last url used in an array that can be used for forward/backward url
        functionality*/
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)

            // when a new page is loaded, save previous page's url to front of url_array
            if (url != null) {
                url_array.add(0, url)
            }
        }
    }
}

