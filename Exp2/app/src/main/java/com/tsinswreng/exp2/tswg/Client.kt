package com.tsinswreng.exp2.tswg
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tsinswreng.exp2.models.Article
import com.tsinswreng.exp2.models.Comment
import com.tsinswreng.exp2.models.User
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Deferred


class Client {
	
	companion object {
		@Volatile //indicate that a variable's value will be modified by different threads
		private var INSTANCE: Client? = null
		
		fun getInstance(): Client {
			return INSTANCE ?: synchronized(this) {
				INSTANCE ?: Client().also { INSTANCE = it }
			}
		}
	}
	val http = Http()
	var baseUrl = "http://10.0.2.2:3000"
	val gson = Gson()
	suspend fun login(name:String, pswd:String):Boolean{
		val map = mapOf(
			"name" to name
			,"pswd" to pswd
		)
		val gson = Gson()
		val json = gson.toJson(map)
		val got = http.post(baseUrl+"/user/login", json).await()
		val status = got.status.value
//		println("zzzz")
//		println(status)
		if(status != 200){
			throw RuntimeException("login failed")
			//return false
		}
		val idStr = got.bodyAsText()
		val user = User.currentUser
		user.userName = name
		user.pswd = pswd
		//user.token = token
		user.id = idStr.toInt()
		return true
	}
	
	suspend fun signUp(name:String, pswd:String):Boolean{
		val map = mapOf(
			"name" to name
			,"pswd" to pswd
		)
		val json = gson.toJson(map)
		val got = http.post(baseUrl+"/user/signUp", json).await()
		val status = got.status.value
//		println("zzzz")
//		println(status)
		if(status != 200){
			throw RuntimeException("signUp failed")
			//return false
		}
		val token = got.bodyAsText()
		val user = User.currentUser
		user.userName = name
		user.pswd = pswd
		user.token = token
		return true
	}
	
	suspend fun getAllArticle():List<Article>{
		val json = http.getStr(baseUrl+"/article/allArticle").await()
		//gson.fromJson<List<Article>>(json, Article::class.java)
//TypeToken 是 Gson 提供的一个辅助类，用于在运行时保留泛型类型信息。
//object : TypeToken<List<Article>>() {} 语法用于创建一个继承自 TypeToken<List<Article>> 的匿名对象。
//.type 属性用于从 TypeToken 实例中获取泛型类型的 Type 对象。这个 Type 对象包含了完整的类型信息，包括泛型参数。
		val listType = object : TypeToken<List<Article>>() {}.type
		val articles: MutableList<Article> = gson.fromJson(json, listType)
		return articles
	}
	
	fun getFmtWeather():Deferred<String>{
		return http.getStr(baseUrl+"/user/fmtWeather")
	}
	
	suspend fun postComment(articleId:Int, userId:Int, score:Int, text:String):String{
		val map = mapOf(
			"article_id" to articleId
			,"user_id" to userId
			,"score" to score
			,"text" to text
		)
		val json = gson.toJson(map)
		val got = http.post(baseUrl+"/article/addComment", json).await()
		val status = got.status.value
		if(status != 200){
			throw RuntimeException("comment failed")
			//return false
		}
		val resp = got.bodyAsText()
		return resp
		//return true
	}
	
	suspend fun getCommentByArticleId(id:Int):MutableList<Comment>{
		val json = http.getStr(baseUrl+"/article/seekComments?article_id=${id}").await()
		val listType = object : TypeToken<List<Comment>>() {}.type
		val comments: MutableList<Comment> = gson.fromJson(json, listType)
		return comments
	}
	

}

/*

		val client = Client()
		runBlocking {
			try {
				val got = client.login("hjr", "1169")
				println(got + "aaaa")
			}catch (e :Exception){
				System.err.println(e)
				println(e.stackTrace)
				println("aaaa")
			}
			
		}
 
* */