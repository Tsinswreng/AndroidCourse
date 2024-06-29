package com.tsinswreng.exp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		// 获取用户名输入框和登录按钮的引用
		val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
		val buttonLogin = findViewById<Button>(R.id.buttonLogin)

		// 设置登录按钮的点击监听器
		buttonLogin.setOnClickListener {
			// 获取用户名输入框的内容
			val username = editTextUsername.text.toString()
			// 打印用户名到控制台
			println("用户名: $username")
		}
	}
}
