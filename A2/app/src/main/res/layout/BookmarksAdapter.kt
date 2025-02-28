package layout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val textView: TextView = view.findViewById(R.id.itemTextView)
}

class BookmarksAdapter(val data: Array<String>) : RecyclerView.Adapter<TextItemViewHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //val view = layoutInflater.inflate("", parent, false)
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        holder.textView.text = data[position]
    }
}