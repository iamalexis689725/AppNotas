package com.example.appnotas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListNoteAdapter(
    private val noteList: ArrayList<Note>,
    private val listener: OnNoteClickListener
) : RecyclerView.Adapter<ListNoteAdapter.ListNoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListNoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_note_layout, parent, false)
        return ListNoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ListNoteViewHolder, position: Int) {
        holder.bind(noteList[position], listener)
    }

    fun itemAdded(note: Note) {
        noteList.add(note)
        notifyItemInserted(noteList.size - 1)
    }

    fun itemUpdated(note: Note) {
        val index = noteList.indexOf(note)
        if (index != -1) {
            noteList[index] = note
            notifyItemChanged(index)
        }
    }

    fun itemDeleted(note: Note) {
        val index = noteList.indexOf(note)
        noteList.removeAt(index)
        notifyItemRemoved(index)
    }

    class ListNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var lblTitle = itemView.findViewById<TextView>(R.id.lblTitle)
        private var lblDescription = itemView.findViewById<TextView>(R.id.lblDescription)
        private var panel = itemView.findViewById<TextView>(R.id.panel)

        private var deleteButton = itemView.findViewById<ImageButton>(R.id.deleteButton)
        private var editButton = itemView.findViewById<ImageButton>(R.id.editButton)
        private var btnColor = itemView.findViewById<ImageButton>(R.id.btnColor)



        fun bind(note: Note, listener: OnNoteClickListener) {
            lblTitle.text = note.title
            lblDescription.text = note.description
            panel.setBackgroundColor(note.backgroundColor) // Establece el color de fondo

            editButton.setOnClickListener {
                listener.onNoteEditClickListener(note)
            }

            deleteButton.setOnClickListener {
                listener.onNoteDeleteClickListener(note)
            }

            btnColor.setOnClickListener {
                listener.onColorButtonClick(note)
            }
        }
    }

    public interface OnNoteClickListener {
        fun onNoteEditClickListener(note: Note)
        fun onNoteDeleteClickListener(note: Note)
        fun onColorButtonClick(note: Note)
    }
}
