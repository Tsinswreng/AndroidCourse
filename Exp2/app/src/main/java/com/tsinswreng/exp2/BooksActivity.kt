package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.tsinswreng.exp2.models.Article
import kotlinx.coroutines.*
import com.tsinswreng.exp2.tswg.Client

class BooksActivity : AppCompatActivity() {
    val client = Client()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        
        // 获取传递的用户名
        val username = intent.getStringExtra("USERNAME")
        
        // 显示欢迎信息
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        welcomeTextView.text = "欢迎您 $username"
        welcomeTextView.visibility = TextView.VISIBLE
        
        // 初始化RecyclerView
        val recyclerViewBooks = findViewById<RecyclerView>(R.id.recyclerViewBooks)
        recyclerViewBooks.layoutManager = GridLayoutManager(this, 2)
        
        // 加载书籍列表数据
        GlobalScope.launch(Dispatchers.Main) {
            val booksList = fetchBooksListFromServer()
            recyclerViewBooks.adapter = BookAdapter(booksList) { bookId ->
                // 书籍点击事件，跳转到 ReadActivity
                val readIntent = Intent(this@BooksActivity, ReadActivity::class.java).apply {
//                    println("zzzz")
//                    println(bookId)
                    putExtra("BOOK_ID", bookId.toString())
                    //putExtra("BOOK_TITLE", bookTitle)
                }
                startActivity(readIntent)
            }
        }
    }
    
    private suspend fun fetchBooksListFromServer(): List<Article> {
        val z = this
        return withContext(Dispatchers.IO) {
            val articles = z.client.getAllArticle()
            Article.curArticles = articles.toMutableList()
//            val ans = mutableListOf<Pair<String, String>>()
//            for(obj in articles){
//                val pair = Pair(obj.title, obj.author)
//                ans.add(pair)
//            }
//            ans
            articles
//            listOf(
//                "Book 1" to "Author A",
//                "Book 2" to "Author B",
//                "Book 3" to "Author C",
//                "Book 4" to "Author D",
//                "Book 5" to "Author E",
//                "Book 6" to "Author F"
//            )
        }
    }
}