package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import kotlinx.coroutines.*

class ReadActivity : AppCompatActivity() {
    private lateinit var chapterTitle: TextView
    private lateinit var textViewContent: TextView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        // 获取控件引用
        chapterTitle = findViewById(R.id.chapterTitle)
        textViewContent = findViewById(R.id.textViewContent)
        scrollView = findViewById(R.id.scrollView)

        // 获取传递的书籍标题
        val bookTitle = intent.getStringExtra("BOOK_TITLE")

        // 设置章节标题
        chapterTitle.text = "正在阅读: $bookTitle"

        // 获取并显示内容
        GlobalScope.launch(Dispatchers.Main) {
            val content = fetchBookContentFromServer(bookTitle)
            textViewContent.text = content
        }

        // 设置导航按钮的点击事件
        findViewById<Button>(R.id.prevChapterButton).setOnClickListener {
            // 处理上一章的逻辑
        }
        findViewById<Button>(R.id.nextChapterButton).setOnClickListener {
            // 处理下一章的逻辑
        }

        // 评论按钮点击事件
        findViewById<Button>(R.id.commentButton).setOnClickListener {
            val commentIntent = Intent(this, CommentActivity::class.java).apply {
                putExtra("BOOK_TITLE", bookTitle)
            }
            startActivity(commentIntent)
        }
    }

    private suspend fun fetchBookContentFromServer(bookTitle: String?): String {
        return withContext(Dispatchers.IO) {
            // 模拟从服务器获取数据，实际应用中使用网络请求库（如 Retrofit）
            // 以下仅为模拟数据
            Thread.sleep(2000) // 模拟网络延迟
            "滚滚长江东逝水，浪花淘尽英雄。是非成败,转头空。...\n...\n..."
        }
    }
}
