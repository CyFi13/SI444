package edu.usna.mobileos.p_alvistristen
/**
 * Filename: HomepageAdapter.kt
 * Author: MIDN Tristen Alvis (260102)
 * Description: provides functionality for homepage recycler view in MainActivity
 */
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TextItemViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {
    val textView: TextView = view.findViewById(R.id.textItemView)

    /* set textView text and onClickListener */
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
        menu?.add(adapterPosition, R.id.save_routine, 2, "Save Routine")
    }
}

class HomepageAdapter(val data: ArrayList<Routine>): RecyclerView.Adapter<TextItemViewHolder>() {
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.item_layout, parent, false)
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        holder.bind(data[position])
    }
}