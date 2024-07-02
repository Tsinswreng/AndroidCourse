package com.tsinswreng.exp2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MyBroadcastReceiver : BroadcastReceiver() {
	override fun onReceive(context: Context?, intent: Intent?) {
		if (intent != null) {
			when (intent.action) {
//				Intent.ACTION_SCREEN_ON -> {
//
//				}
				Intent.ACTION_BATTERY_LOW -> {
					Toast.makeText(context, "电池电量低", Toast.LENGTH_LONG)
				}
//				Intent.ACTION_POWER_CONNECTED -> {
//				}
			}
		}
	}
}
