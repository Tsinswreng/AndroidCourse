package com.tsinswreng.exp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
class MainActivity : AppCompatActivity() {
	private lateinit var position: Position
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_login)
		// 初始化 Position 对象
		position = Position(this)
		// 请求位置信息
		position.getLocation { latitude, longitude ->
			Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
			println("zzzz-")
			println(latitude)
			println(longitude)
		}
		
	}
	
	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		
		// 将权限结果传递给 Position 对象，并重新尝试获取位置信息
		position.onRequestPermissionsResult(requestCode, grantResults) { latitude, longitude ->
			Toast.makeText(this, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_LONG).show()
		}
	}
	


}
