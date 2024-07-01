package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import kotlinx.coroutines.*

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
        
        // 加载书籍列表数据
        GlobalScope.launch(Dispatchers.Main) {
            val booksList = fetchBooksListFromServer()
            recyclerViewBooks.adapter = BookAdapter(booksList) { bookTitle ->
                // 书籍点击事件，跳转到 ReadActivity
                val readIntent = Intent(this@BooksActivity, ReadActivity::class.java).apply {
                    putExtra("BOOK_TITLE", bookTitle)
                }
                startActivity(readIntent)
            }
        }
    }
    
    private suspend fun fetchBooksListFromServer(): List<Pair<String, String>> {
        return withContext(Dispatchers.IO) {
            // 模拟从服务器获取数据，实际应用中使用网络请求库（如 Retrofit）
            // 以下仅为模拟数据
            Thread.sleep(2000) // 模拟网络延迟
            listOf(
                "Book 1" to "Author A",
                "Book 2" to "Author B",
                "Book 3" to "Author C",
                "Book 4" to "Author D",
                "Book 5" to "Author E",
                "Book 6" to "Author F"
            )
        }
    }
}