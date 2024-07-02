package com.tsinswreng.exp2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.tsinswreng.exp2.models.Article
import kotlinx.coroutines.*
import com.tsinswreng.exp2.tswg.Client
import com.tsinswreng.exp2.tswg.then

class BooksActivity : AppCompatActivity() {
    val client = Client()
    private lateinit var position: Position
    var latitude = 0.0
    var longitude = 0.0
    
    override fun onCreate(savedInstanceState: Bundle?) {
        val z = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)
        
        
        // 获取传递的用户名
        val username = intent.getStringExtra("USERNAME")
        // 显示欢迎信息
        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
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
        
        position = Position(this)
        // 请求位置信息
        val weatherTextView = findViewById<TextView>(R.id.weather)
        val positionTextView = findViewById<TextView>(R.id.position)
        welcomeTextView.text = "欢迎您 $username\n"
        welcomeTextView.visibility = TextView.VISIBLE
        weatherTextView.visibility = TextView.VISIBLE
        positionTextView.visibility = TextView.VISIBLE
        position.getLocation { latitude, longitude ->
            z.latitude = latitude
            z.longitude = longitude
            positionTextView.text = "${z.fmtPosition(z.latitude, z.longitude)}"
            //Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
//            println("zzzz-")
//            println(latitude)
//            println(longitude)
        }
        
        client.getFmtWeather().then { fmt ->
            weatherTextView.text = "${fmt}"
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
    
    fun fmtPosition(la:Double, lo:Double):String{
        val dig = 2
        return "经度:%.${2}f".format(la) + ("纬度:%.${2}f".format(lo))
    }
    
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        // 将权限结果传递给 Position 对象，并重新尝试获取位置信息
        position.onRequestPermissionsResult(requestCode, grantResults) { latitude, longitude ->
            Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
        }
    }
}