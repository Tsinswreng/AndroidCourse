package com.tsinswreng.exp2.models

class User {
	companion object{
		var currentUser = User()
	}
	var userName:String = ""
	var pswd:String = ""
	var token:String = ""
}