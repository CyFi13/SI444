package edu.usna.mobileos.a3_alvistristen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.todoTextView)

    /* sets the TextView text and onClickListener */
    fun bind(todo: String, listener: ToDoListener) {
        textView.text = todo
        textView.setOnClickListener{ listener.onItemClick(todo) }
    }
}

interface ToDoListener {
    fun onItemClick(todo: String)
}

class ToDoAdapter(val data: ArrayList<String>, val listener: ToDoListener) : RecyclerView.Adapter<TextItemViewHolder>() {
    /* returns number of items */
    override fun getItemCount() = data.size

    /* instructions for creating the ViewHolder for the TextViews */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.todo_layout, parent, false)
        return TextItemViewHolder(view)
    }

    /* binds url data and listener to the ViewHolder */
    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        holder.bind(data[position], listener)
    }
}
