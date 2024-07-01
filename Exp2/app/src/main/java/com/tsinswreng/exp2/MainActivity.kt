package com.tsinswreng.exp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

	private lateinit var position: Position
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val firstIntent = Intent(this, LoginActivity::class.java)
		startActivity(firstIntent)
	}
	
}
