package com.tsinswreng.exp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
//import io.ktor.client.plugins.contentnegotiation.*
//import io.ktor.serialization.kotlinx.json.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class TestJson{
	var name = "json"
	var age = "18"
	var arr = mutableListOf<String>("a", "b", "cd")
}


@Serializable
data class Post(val userId: Int, val id: Int, val title: String, val body: String)

fun testNet() {
	// 使用 CIO 引擎创建 HttpClient
	println("a1")
	val client = HttpClient(CIO)
	
	// 使用协程作用域启动异步任务
	val scope = CoroutineScope(Dispatchers.Default)
	scope.launch {
		println("a2")
		try {
			// 发起 GET 请求，获取 JSON 数据
			println("a3")
			//val post = client.get("https://jsonplaceholder.typicode.com/posts/1")
			val resp = client.get("http://10.0.2.2:1919")
			println(resp.bodyAsText())
			println("a4")//-
			// 打印获取的数据
			println("got114514:")
			println(resp)
		} catch (e: Exception) {
			println("eeeeeeeeee")
			println("Error: ${e.message}")
		} finally {
			// 关闭 HttpClient
			client.close()
			println("a5") //+
		}
		println("a6") //+
	}
	
	// 等待协程任务完成
	//Thread.sleep(2000)
}

//class MainActivity : AppCompatActivity() {
//	override fun onCreate(savedInstanceState: Bundle?) {
//		super.onCreate(savedInstanceState)
//		setContentView(R.layout.activity_main)
//		val gson = Gson()
//		val gotGson = gson.toJson(TestJson())
//		testNet()
//		println(gotGson)
//	}
//}