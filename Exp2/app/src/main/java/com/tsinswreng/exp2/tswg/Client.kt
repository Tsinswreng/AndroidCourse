package com.tsinswreng.exp2.tswg
import com.google.gson.Gson
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Deferred

class Client {
	val http = Http()
	var baseUrl = "http://10.0.2.2:3000"
	suspend fun login(name:String, pswd:String):String{
		val map = mapOf(
			"name" to name
			,"pswd" to pswd
		)
		val gson = Gson()
		val json = gson.toJson(map)
		val got = http.post(baseUrl+"/user/login", json).await()
//		if(!got.status.equals(200)){
//			throw RuntimeException("login failed")
//		}
		return got.bodyAsText()
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