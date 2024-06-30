package com.tsinswreng.exp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val booksList: List<String>, private val onClick: (String) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bookTitle.text = booksList[position]
    }

    override fun getItemCount() = booksList.size

    class BookViewHolder(itemView: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)

        init {
            itemView.setOnClickListener {
                onClick(bookTitle.text.toString())
            }
        }
    }
}