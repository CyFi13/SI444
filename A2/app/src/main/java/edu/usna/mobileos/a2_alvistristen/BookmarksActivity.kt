package edu.usna.mobileos.a2_alvistristen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/** BookmarksActivity
 * Author: MIDN Tristen Alvis, m260102@usna.edu
 * Date: 3 March 2025
 * Description: this is the second Activity for Assignment 2. It contains a recycler view that
 * will show a basic list of saved URLs that were passed from the MainActivity. The URLs are
 * displayed as TextViews in the RecyclerView that can be clicked to finish this activity and
 * to load the URL in the MainActivity.
 */

/* BookmarksActivity
* Author: MIDN Tristen Alvis, m260102@usna.edu
* Date: 3 March 2025
* Description: the BookmarksActivity class is opened in the MainActivity and receives an intent
* that contains a list of saved bookmark URLs. These URLs are displayed in multiple TextViews inside
* a RecyclerView. Clicking on any of the URLs finishes this activity and opens the URL page in the
* MainActivity.
* */
class BookmarksActivity : AppCompatActivity(), BookmarkListener {
    lateinit var bookmarks : ArrayList<String>
    /* initializes the necessary views in BookmarskActivity */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarks)

        val bookmarksView : RecyclerView = findViewById(R.id.bookmark_list)

        Log.i("SI444", "test1")
        val stringlist = intent.getStringArrayListExtra("bookmarks")
        Log.i("SI444", "test2")

        if(stringlist.isNullOrEmpty()) {    // if no bookmarks provided, populate list with CS website
            bookmarks = ArrayList<String>()
            bookmarks.add("https://www.usna.edu/CS")
        }
        else
            bookmarks = stringlist

        Log.i("SI444", "bookmark list" + bookmarks.toString())
        val adapter = BookmarkAdapter(bookmarks, this)
        bookmarksView.adapter = adapter
    }

    /* this method overrides onItemClick() and will finish this activity and send the result
    * URL value back to MainActivity
    */
    override fun onItemClick(bookmark: String) {
        val resultIntent = Intent()
        resultIntent.putExtra("url", bookmark)

        setResult(RESULT_OK, resultIntent)
        finish()
    }
}