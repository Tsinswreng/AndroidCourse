package com.tsinswreng.exp2.models

data class Article(
	val id: Int,
	val title: String,
	val author: String,
	val content: String,
	val ct: Long
){
	//当前文章列表
	companion object{
		var curArticles = mutableListOf<Article>()
		//由id线性查找文章
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

