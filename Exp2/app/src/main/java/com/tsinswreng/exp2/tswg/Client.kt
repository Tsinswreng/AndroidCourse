package com.tsinswreng.exp2.tswg
import com.google.gson.Gson
import com.tsinswreng.exp2.svc.User
import io.ktor.client.statement.bodyAsText


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
		 val token = got.bodyAsText()
		val user = User.currentUser
		user.userName = name
		user.pswd = pswd
		user.token = token
		return true
	}
	
	suspend fun signUp(name:String, pswd:String):Boolean{
		val map = mapOf(
			"name" to name
			,"pswd" to pswd
		)
		val gson = Gson()
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