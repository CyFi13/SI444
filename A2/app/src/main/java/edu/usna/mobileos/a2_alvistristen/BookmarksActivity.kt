package edu.usna.mobileos.a2_alvistristen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class BookmarksActivity : AppCompatActivity() {
    lateinit var bookmarks : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        val bookmarksView : RecyclerView = findViewById(R.id.bookmark_list)
        //val bookmarkArray = resources.getSTringArray()
        bookmarks = intent.getStringArrayExtra("bookmarks")!!


    }
}