package com.tsinswreng.exp2

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast

class MyService : Service() {
	
	private val handler = Handler(Looper.getMainLooper())
	private val interval: Long = 1000*30 // 1min
	protected var totalTime:Long = 0
	
	fun updateTotalTime(){
		this.totalTime += this.interval
	}
	
	private val runnable = object : Runnable {
		override fun run() {
			performTask()
			handler.postDelayed(this, interval)
			updateTotalTime()
			Toast.makeText(
				this@MyService
				, "您已使用${totalTime*1.0/60000}分钟,请注意用眼"
				, Toast.LENGTH_LONG
			).show()
		}
	}
	
	override fun onCreate() {
		super.onCreate()
		Log.d("MyService", "Service Created")
		handler.post(runnable) // 开始定时任务
	}
	
	override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show()
		Log.d("MyService", "Service Started")
		return START_STICKY
	}
	
	override fun onDestroy() {
		super.onDestroy()
		Log.d("MyService", "Service Destroyed")
		handler.removeCallbacks(runnable) // 停止定时任务
	}
	
	override fun onBind(intent: Intent?): IBinder? {
		return null
	}
	
	private fun performTask() {
		Log.d("MyService", "Performing Task")
		// 在这里执行你的定时任务
	}
}
