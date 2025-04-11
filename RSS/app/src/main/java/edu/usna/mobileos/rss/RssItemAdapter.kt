package edu.usna.mobileos.rss

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RssItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.itemTextView)

    fun bind(item: RssItem) {
        textView.text = item.title
    }
}

class RssItemAdapter(val data: ArrayList<RssItem>): RecyclerView.Adapter<RssItemViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_layout, parent, false)
        return RssItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RssItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size
}


