package com.tsinswreng.exp2.models

class User {
	//当前用户
	companion object{ //伴随对象。相当于java的静态成员
		var currentUser = User()
	}
	var id:Int = 0 //
	var userName:String = "" //用户名
	var pswd:String = "" // 密码
	var token:String = "" // 令牌。未使用
}