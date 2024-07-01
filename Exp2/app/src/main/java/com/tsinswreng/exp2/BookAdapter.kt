package com.tsinswreng.exp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookAdapter(private val booksList: List<Pair<String, String>>, private val onClick: (String) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view, onClick)
    }
    
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val (bookTitle, bookAuthor) = booksList[position]
        holder.bookTitle.text = bookTitle
        holder.bookAuthor.text = bookAuthor
    }
    
    override fun getItemCount() = booksList.size
    
    class BookViewHolder(itemView: View, val onClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        
        init {
            itemView.setOnClickListener {
                onClick(bookTitle.text.toString())
            }
        }
    }
}