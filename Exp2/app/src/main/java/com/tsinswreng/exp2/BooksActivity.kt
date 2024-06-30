package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class BooksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        // 获取传递的用户名
        val username = intent.getStringExtra("USERNAME")

        // 显示欢迎信息
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "Welcome $username"
        welcomeTextView.visibility = TextView.VISIBLE

        // 初始化RecyclerView
        val recyclerViewBooks = findViewById<RecyclerView>(R.id.recyclerViewBooks)
        recyclerViewBooks.layoutManager = GridLayoutManager(this, 2)
        recyclerViewBooks.adapter = BookAdapter(getBooksList()) { bookTitle ->
            // 书籍点击事件，跳转到 ReadActivity
            val readIntent = Intent(this, ReadActivity::class.java).apply {
                putExtra("BOOK_TITLE", bookTitle)
            }
            startActivity(readIntent)
        }
    }

    private fun getBooksList(): List<String> {
        // 用例子数据填充书籍名单
        return listOf("Book 1", "Book 2", "Book 3", "Book 4", "Book 5", "Book 6")
    }
}