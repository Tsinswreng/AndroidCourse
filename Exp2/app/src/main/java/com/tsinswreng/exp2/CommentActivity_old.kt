//package com.tsinswreng.exp2
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.coroutines.*
//import com.tsinswreng.exp2.models.Article
//import com.tsinswreng.exp2.models.User
//import com.tsinswreng.exp2.tswg.Client
//class CommentActivity_old : AppCompatActivity() {
//
//    private lateinit var editTextComment: EditText
//    private lateinit var recyclerViewComments: RecyclerView
//    protected var article_id:Int=0
//    protected lateinit var article:Article
//    protected val client = Client.getInstance()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_comment)
//
//        // 获取控件引用
//        editTextComment = findViewById(R.id.editTextComment)
//        val buttonSubmitComment = findViewById<Button>(R.id.buttonSubmitComment)
//        recyclerViewComments = findViewById(R.id.recyclerViewComments)
//
//        // 获取传递的书籍标题
//        val idStr = intent.getStringExtra("BOOK_ID")
//        this.article_id = idStr!!.toInt()
//        this.article = Article.seekArticleById(article_id)!!
//        val bookTitle = article.title
//        //val bookId = intent.getStringExtra("BOOK_ID")
//        // 设置RecyclerView
//        recyclerViewComments.layoutManager = LinearLayoutManager(this)
//        recyclerViewComments.adapter = CommentAdapter(emptyList())
//
//        // 加载评论内容
//        lifecycleScope.launch(Dispatchers.Main) {
//            val comments = fetchCommentsFromServer(bookTitle)
//            runOnUiThread {
//                recyclerViewComments.adapter = CommentAdapter(comments)
//            }
//        }
//
//        // 提交评论按钮点击事件
//        buttonSubmitComment.setOnClickListener {
//            val comment = editTextComment.text.toString()
//            lifecycleScope.launch(Dispatchers.Main) {
//                val success = submitCommentToServer(comment)
//                if (success) {
//                    runOnUiThread {
//                        Toast.makeText(this@CommentActivity, "评论提交成功", Toast.LENGTH_SHORT).show()
//                    }
//                    // 刷新评论区
//                    val comments = fetchCommentsFromServer(bookTitle)
//                    runOnUiThread{
//                        recyclerViewComments.adapter = CommentAdapter(comments)
//                        editTextComment.text.clear()
//                    }
//                } else {
//                    runOnUiThread{
//                        Toast.makeText(this@CommentActivity, "评论提交失败", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
////            lifecycleScope.launch(Dispatchers.Main) {
////
////            }
//        }
//    }
//
//    private suspend fun fetchCommentsFromServer(bookTitle: String?): List<String> {
//        val z = this
//        return withContext(Dispatchers.IO) {
//            val comments = z.client.getCommentByArticleId(z.article_id)
//            val ans = mutableListOf<String>()
//            for(e in comments){
//                ans.add(e.user_id.toString()+" "+e.text)
//            }
//            //listOf("用户A: 评论1", "用户B: 评论2", "用户C: 评论3")
//            ans
//        }
//    }
//
//    private suspend fun submitCommentToServer(comment: String): Boolean {
//        return withContext(Dispatchers.IO) {
//            try {
//                client.postComment(
//                    article.id,
//                    User.currentUser.id,
//                    100,
//                    comment
//                )
//                true
//            } catch (e: Exception) {
//                System.err.println("failed_comment")
//                System.err.println(e) //  kotlinx.coroutines.JobCancellationException: Parent job is Completed; job=SupervisorJobImpl{Completed}@638b0c
//                false
//            }
//        }
////        val z = this
////        val book = z.article
////        try {
////            client.postComment(
////                book.id
////                , User.currentUser.id
////                , 100
////                , comment
////            )
////            return true
////        } catch (e: Exception) {
////            System.err.println("failed_comment")
////            System.err.println(e) //  kotlinx.coroutines.JobCancellationException: Parent job is Completed; job=SupervisorJobImpl{Completed}@638b0c
////            return false
////        }
////        return false
//    }
//}
//
//
////        return withContext(Dispatchers.IO) {
////            return@withContext try {
////                client.postComment(
////                    book.id
////                    , User.currentUser.id
////                    , 100
////                    , comment
////                )
////                true
////            } catch (e: Exception) {
////                System.err.println("failed_comment")
////                System.err.println(e)
////                false
////            }
////        }