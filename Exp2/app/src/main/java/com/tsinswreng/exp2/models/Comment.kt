package com.tsinswreng.exp2.models

data class Comment(
	val id: Int,
	val article_id:Int,
	val user_id:Int,
	val text:String,
	val score:Int,
	val ct: Long
)