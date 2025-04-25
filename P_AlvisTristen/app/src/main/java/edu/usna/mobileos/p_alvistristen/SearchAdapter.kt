package edu.usna.mobileos.p_alvistristen

import android.graphics.Color
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Filename: SearchAdapter.kt
 * Author: MIDN Tristen Alvis (260102)
 * Description: provides functionality for search recycler view in SearchActivity
 */

class TextItemViewHolder2(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener{
    val textView: TextView = view.findViewById(R.id.textItemView)

    fun bind(item: Routine) {
        textView.text = item.title
        textView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(adapterPosition, R.id.routine_details, 1, "Routine Details")
        menu?.add(adapterPosition, R.id.select_routine, 2, "Select")
        menu?.add(adapterPosition, R.id.delete_routine, 3, "Delete")
    }
}

class SearchpageAdapter(data: ArrayList<Routine>): RecyclerView.Adapter<TextItemViewHolder2>() {
    var data: ArrayList<Routine> = data
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder2 {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_layout, parent, false)
        return TextItemViewHolder2(view)
    }
    override fun onBindViewHolder(holder: TextItemViewHolder2, position: Int) {
        holder.bind(data[position])
    }

    // implemented w/ assistance from geeksforgeeks and androiddeveloper.com
    fun filterList(filterList: ArrayList<Routine>) {
        data = filterList
    }
}