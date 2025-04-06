package edu.usna.mobileos.a3_alvistristen

/**
 * Filename: ToDoAdapter.kt
 * Author: MIDN Tristen Alvis (260102)
 * Date: 6Apr2025
 */
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

/**
 * A custom ViewHolder to store information about ToDo objects in TextViews
 */
class TextItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val textView: TextView = view.findViewById(R.id.todoTextView)

    /* sets the TextView text and onClickListener */
    fun bind(todo: ToDo) {
        textView.text = todo.title
        textView.setOnCreateContextMenuListener(this)
    }

    /* adds a context menu to each textview item */
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menu?.add(adapterPosition, R.id.show_detail, 1, "Show")
        menu?.add(adapterPosition, R.id.edit_detail, 2, "Edit")
        menu?.add(adapterPosition, R.id.delete_item, 3, "Delete")
    }
}

/**
 * custom adapter for displaying the textView items in a recycler view
 */
class ToDoAdapter(val data: ArrayList<ToDo>) : RecyclerView.Adapter<TextItemViewHolder>() {
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
        holder.bind(data[position])
    }
}
