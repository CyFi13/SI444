package edu.usna.mobileos.p_alvistristen

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Filename: SearchAdapter.kt
 * Author: MIDN Tristen Alvis (260102)
 * Description: provides functionality for search recycler view in SearchActivity
 */

class TextItemViewHolder2(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {
    val textView: TextView = view.findViewById(R.id.textItemView)

    fun bind(item: Routine) {
        textView.text = item.title
        textView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}

class SearchpageAdapter(val data: ArrayList<Routine>): RecyclerView.Adapter<TextItemViewHolder>() {
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}