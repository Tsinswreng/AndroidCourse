package com.tsinswreng.exp2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class CommentActivity : AppCompatActivity() {

    private lateinit var editTextComment: EditText
    private lateinit var recyclerViewComments: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        // 获取控件引用
        editTextComment = findViewById(R.id.editTextComment)
        val buttonSubmitComment = findViewById<Button>(R.id.buttonSubmitComment)
        recyclerViewComments = findViewById(R.id.recyclerViewComments)

        // 获取传递的书籍标题
        val bookTitle = intent.getStringExtra("BOOK_TITLE")
        //val bookId = intent.getStringExtra("BOOK_ID")
        // 设置RecyclerView
        recyclerViewComments.layoutManager = LinearLayoutManager(this)
        recyclerViewComments.adapter = CommentAdapter(emptyList())

        // 加载评论内容
        GlobalScope.launch(Dispatchers.Main) {
            val comments = fetchCommentsFromServer(bookTitle)
            recyclerViewComments.adapter = CommentAdapter(comments)
        }

        // 提交评论按钮点击事件
        buttonSubmitComment.setOnClickListener {
            val comment = editTextComment.text.toString()
            GlobalScope.launch(Dispatchers.Main) {
                val success = submitCommentToServer(bookTitle, comment)
                if (success) {
                    Toast.makeText(this@CommentActivity, "评论提交成功", Toast.LENGTH_SHORT).show()
                    // 刷新评论区
                    val comments = fetchCommentsFromServer(bookTitle)
                    recyclerViewComments.adapter = CommentAdapter(comments)
                    editTextComment.text.clear()
                } else {
                    Toast.makeText(this@CommentActivity, "评论提交失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun fetchCommentsFromServer(bookTitle: String?): List<String> {
        return withContext(Dispatchers.IO) {
            // 模拟从服务器获取数据，实际应用中使用网络请求库（如 Retrofit）
            // 以下仅为模拟数据
            Thread.sleep(2000) // 模拟网络延迟
            listOf("用户A: 评论1", "用户B: 评论2", "用户C: 评论3")
        }
    }

    private suspend fun submitCommentToServer(bookTitle: String?, comment: String): Boolean {
        return withContext(Dispatchers.IO) {
            // 模拟网络请求提交评论，实际应用中应使用网络请求库（如 Retrofit）
            return@withContext try {
                // 假装我们在提交评论
                Thread.sleep(2000)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}