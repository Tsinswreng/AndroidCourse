package com.tsinswreng.exp2.tswg

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.CoroutineContext
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.contentType

class Http {
	val client = HttpClient(CIO)
	private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
	fun getStr(url: String): Deferred<String> {
		return scope.async(Dispatchers.IO) {
			try {
				val response = client.get(url)
				response.bodyAsText()
			} catch (e: Exception) {
				throw e
			} finally {
				client.close()
			}
		}
	}
	
	fun get(url: String): Deferred<HttpResponse> {
		return scope.async(Dispatchers.IO) {
			try {
				val response = client.get(url)
				response
			} catch (e: Exception) {
				throw e
			} finally {
				client.close()
			}
		}
	}
	
	
	fun post(url: String, payload: Any): Deferred<HttpResponse> {
		return scope.async(Dispatchers.IO) {
			try {
				val response = client.post(url) {
					if(payload is String){
						contentType(io.ktor.http.ContentType.Application.Json)
						setBody(payload)
					}else{
						setBody(payload)
					}
				}
				response
			} catch (e: Exception) {
				throw e
			}
		}
	}
	
	fun strPost(url: String, payload: Any): Deferred<String> {
		return scope.async(Dispatchers.IO) {
			try {
				val response = client.post(url) {
					if(payload is String){
						contentType(io.ktor.http.ContentType.Application.Json)
						setBody(payload)
					}else{
						setBody(payload)
					}
				}
				response.bodyAsText()
			} catch (e: Exception) {
				throw e
			}
		}
	}

	fun close() {
		client.close()
	}
}

fun <T> Deferred<T>.then(context: CoroutineContext = Dispatchers.Main, onFulfilled: (T) -> Unit): Deferred<T> {
	return this.apply {
		invokeOnCompletion { throwable ->
			if (throwable == null) {
				runBlocking(context) {
					onFulfilled(this@then.await())
				}
			}
		}
	}
}

fun <T> Deferred<T>.catch(context: CoroutineContext = Dispatchers.Main, onRejected: (Throwable) -> Unit): Deferred<T> {
	return this.apply {
		invokeOnCompletion { throwable ->
			if (throwable != null) {
				runBlocking(context) {
					onRejected(throwable)
				}
			}
		}
	}
}