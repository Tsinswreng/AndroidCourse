package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import kotlinx.coroutines.*
import com.tsinswreng.exp2.models.Article
class ReadActivity : AppCompatActivity() {
    private lateinit var chapterTitle: TextView
    private lateinit var textViewContent: TextView
    private lateinit var scrollView: ScrollView
    private lateinit var article:Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        // 获取控件引用
        chapterTitle = findViewById(R.id.chapterTitle)
        textViewContent = findViewById(R.id.textViewContent)
        scrollView = findViewById(R.id.scrollView)

        // 获取传递的书籍标题
        //val bookTitle = intent.getStringExtra("BOOK_TITLE")
        val bookIdStr = intent.getStringExtra("BOOK_ID")
//        println("bookIdStr")
//        println(bookIdStr)
        val bookId = bookIdStr!!.toInt()
        article = Article.seekArticleById(bookId)!!
        val bookTitle = article.title
        // 设置章节标题
        chapterTitle.text = article.title

        // 获取并显示内容
        GlobalScope.launch(Dispatchers.Main) {
            val content = fetchBookContentFromServer(bookTitle)
            textViewContent.text = content
        }

//        // 设置导航按钮的点击事件
//        findViewById<Button>(R.id.prevChapterButton).setOnClickListener {
//            // 处理上一章的逻辑
//        }
//        findViewById<Button>(R.id.nextChapterButton).setOnClickListener {
//            // 处理下一章的逻辑
//        }

        // 评论按钮点击事件
        findViewById<Button>(R.id.commentButton).setOnClickListener {
            val z = this
            val commentIntent = Intent(this, CommentActivity::class.java).apply {
                //putExtra("BOOK_TITLE", bookTitle)
                putExtra("BOOK_ID", z.article.id.toString())
            }
            startActivity(commentIntent)
        }
    }

    private suspend fun fetchBookContentFromServer(bookTitle: String?): String {
        val z = this
        return withContext(Dispatchers.IO) {
            z.article.content
        }
    }
}
