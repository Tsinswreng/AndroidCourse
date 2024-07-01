package com.tsinswreng.exp2.models

data class Article(
	val id: Int,
	val title: String,
	val author: String,
	val content: String,
	val ct: Long
){
	companion object{
		var curArticles = mutableListOf<Article>()
		fun seekArticleById(id:Int):Article?{
			for(e in this.curArticles){
				if(e.id == id){
					return e
				}
			}
			return null
		}
	}
}

