package com.tsinswreng.exp2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tsinswreng.exp2.models.Article


class BookAdapter(
    private val booksList: List<Article>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_item, parent, false)
        return BookViewHolder(view, onClick)
    }
    
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val article = booksList[position]
        holder.bind(article)
    }
    
    override fun getItemCount() = booksList.size
    
    class BookViewHolder(itemView: View, val onClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val bookTitle: TextView = itemView.findViewById(R.id.bookTitle)
        private val bookAuthor: TextView = itemView.findViewById(R.id.bookAuthor)
        private var currentArticle: Article? = null
        
        init {
            itemView.setOnClickListener {
                currentArticle?.let { article ->
                    onClick(article.id)
                }
            }
        }
        
        fun bind(article: Article) {
            currentArticle = article
            bookTitle.text = article.title
            bookAuthor.text = article.author
        }
    }
}
