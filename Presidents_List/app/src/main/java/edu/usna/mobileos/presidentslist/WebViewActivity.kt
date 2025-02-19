package edu.usna.mobileos.presidentslist

import android.net.http.SslError
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)


        var webView : WebView = WebView(this)
        //president = president?.replace(' ', '_')
        val fullURL = "https://en.m.wikipedia.org/wiki/John_Adams"
        webView.loadUrl(fullURL)
        webView.webViewClient = USNAWebViewClient()
    }
}

private class USNAWebViewClient :  WebViewClient() {

    //these errors might be sent due to missing CA
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

}
