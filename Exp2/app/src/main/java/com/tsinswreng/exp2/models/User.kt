package com.tsinswreng.exp2.models

class User {
	//当前用户
	companion object{
		var currentUser = User()
	}
	var id:Int = 0
	var userName:String = ""
	var pswd:String = ""
	var token:String = ""
}