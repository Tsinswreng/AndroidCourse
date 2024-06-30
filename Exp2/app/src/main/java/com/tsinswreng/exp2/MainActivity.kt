package com.tsinswreng.exp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)

		// 获取控件引用
		val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
		val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
		val buttonLogin = findViewById<Button>(R.id.buttonLogin)
		val buttonRegister = findViewById<Button>(R.id.buttonRegister)

		// 注册按钮点击事件
		buttonRegister.setOnClickListener {
			// 跳转到注册页面
			val registerIntent = Intent(this, RegisterActivity::class.java)
			startActivity(registerIntent)
		}

		// 登录按钮点击事件
		buttonLogin.setOnClickListener {
			// 获取用户名和密码
			val username = editTextUsername.text.toString()
			val password = editTextPassword.text.toString()
			// 打印用户名和密码到控制台
			println("用户名: $username aaa 密码: $password aaaaa")

			// 跳转到书架界面，并传递用户名
			val booksIntent = Intent(this, BooksActivity::class.java).apply {
				putExtra("USERNAME", username)
			}
			startActivity(booksIntent)
		}
	}
}
