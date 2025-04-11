package edu.usna.mobileos.rss

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Xml
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var RssItems: ArrayList<RssItem>
    lateinit var RssItemAdapter: RssItemAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RssItems = ArrayList()
        recyclerView = findViewById(R.id.newsRecyclerView)

        // recycler view stuff
        RssItemAdapter = RssItemAdapter(RssItems)
        recyclerView.adapter = RssItemAdapter

        val serviceIntent = Intent(baseContext, RetrieveFeedService::class.java)
        startService(serviceIntent)

        // receiver stuff
        val intentFilter = IntentFilter()
        intentFilter.addAction("WORK_COMPLETE_ACTION")
        registerReceiver(intentReceiver, intentFilter, RECEIVER_NOT_EXPORTED)

        stopService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(intentReceiver)
    }

    private val intentReceiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            RssItems = intent?.getSerializableExtra("items") as ArrayList<RssItem>
            RssItemAdapter = RssItemAdapter(RssItems)
            recyclerView.adapter = RssItemAdapter
        }
    }
}

class RetrieveFeedService: Service() {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        doWork()
        return START_STICKY
    }

    fun doWork() {
        val thread = object: Thread() {
            override fun run() {
                val results = parseRSS("http://www.espn.com/espn/rss/news")

                val broadcastIntent = Intent()
                broadcastIntent.putExtra("items", results)
                broadcastIntent.action = "WORK_COMPLETE_ACTION"
                baseContext.sendBroadcast(broadcastIntent)
            }
        }
        thread.start()
    }

    fun parseRSS(urlString: String): ArrayList<RssItem> {
        val parser = Xml.newPullParser()

        // create URL object from String
        val feedURL = URL(urlString)

        // create InputStream from URL
        val inputStream: InputStream = feedURL.openStream()

        // set XMLPullParser to use the input stream
        parser.setInput(inputStream, null)

        var currentRSSItem: RssItem? = null
        val resultRssList: ArrayList<RssItem> = ArrayList()

        var eventType = parser.eventType
        var done = false

        while (eventType != XmlPullParser.END_DOCUMENT && !done) {
            var name: String? = null
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    name = parser.name
                    if (name =="item") {
                        // a new item element
                        currentRSSItem = RssItem()
                    } else if (currentRSSItem != null) {
                        //we are within an item
                        when(name){
                            "link" -> currentRSSItem.link =parser.nextText()
                            "description" -> currentRSSItem.description = parser.nextText()
                            "pubDate" -> currentRSSItem.pubDate = parser.nextText()
                            "title"-> currentRSSItem.title = parser.nextText()
                        }
                    }
                }
                XmlPullParser.END_TAG -> {
                    name = parser.name
                    if (name=="item" && currentRSSItem != null) {
                        resultRssList.add(currentRSSItem)
                    } else if (name =="channel") {
                        done = true
                    }
                }
            }
            eventType = parser.next()
        }
        return resultRssList
    }
}