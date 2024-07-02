package com.tsinswreng.exp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

	private lateinit var position: Position
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val firstIntent = Intent(this, LoginActivity::class.java)
		startActivity(firstIntent)
		//启动后台服务
		val intent = Intent(this, MyService::class.java)
		startService(intent)
	}
	
}
