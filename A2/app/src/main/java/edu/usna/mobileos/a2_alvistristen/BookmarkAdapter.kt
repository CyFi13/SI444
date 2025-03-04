package edu.usna.mobileos.a2_alvistristen
/* Filename: BookmarkAdapter.kt
 * Author: MIDN Tristen Alvis (260102)
 * Assignment #2: I completed all 4 phases and did the custom app icon for extra credit
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
/* TextItemViewHolder
* Author: MIDN Tristen Alvis, m260102@usna.edu
* Date: 3 March 2025
* Description: overrides the RecyclerView.ViewHolder to display TextViews with URLs that are
* clickable
*/
class TextItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.itemTextView)

    /* sets the TextView text and onClickListener */
    fun bind(bookmark: String, listener: BookmarkListener) {
        textView.text = bookmark
        textView.setOnClickListener{ listener.onItemClick(bookmark) }
    }
}

interface BookmarkListener {
    fun onItemClick(bookmark: String)
}

/* BookmarkAdapter
* Author: MIDN Tristen Alvis, m260102@usna.edu
* Date: 3 March 2025
* Description: provides a way to create viewholder objects and set the data for those views
*/
class BookmarkAdapter(val data: ArrayList<String>, val listener: BookmarkListener) : RecyclerView.Adapter<TextItemViewHolder>() {
    /* returns number of items */
    override fun getItemCount() = data.size

    /* instructions for creating the ViewHolder for the TextViews */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_layout, parent, false)
        return TextItemViewHolder(view)
    }

    /* binds url data and listener to the ViewHolder */
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }
}
