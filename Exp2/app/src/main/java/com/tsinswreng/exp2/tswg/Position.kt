package com.tsinswreng.exp2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Position(private val activity: Activity) {
	
	private lateinit var locationManager: LocationManager
	private lateinit var locationListener: LocationListener
	
	init {
		setupLocationManager()
	}
	
	private fun setupLocationManager() {
		locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
	}
	
	fun getLocation(callback: (latitude: Double, longitude: Double) -> Unit) {
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
		} else {
			startLocationUpdates(callback)
		}
	}
	
	private fun startLocationUpdates(callback: (latitude: Double, longitude: Double) -> Unit) {
		locationListener = object : LocationListener {
			override fun onLocationChanged(location: Location) {
				val latitude = location.latitude
				val longitude = location.longitude
				callback(latitude, longitude)
				stopLocationUpdates() // Optionally stop updates after first location received
			}
			
			override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
			override fun onProviderEnabled(provider: String) {}
			override fun onProviderDisabled(provider: String) {}
		}
		
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10f, locationListener)
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10f, locationListener)
		}
	}
	
	fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray, callback: (latitude: Double, longitude: Double) -> Unit) {
		if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				startLocationUpdates(callback)
			}
		}
	}
	
	private fun stopLocationUpdates() {
		if (::locationManager.isInitialized && ::locationListener.isInitialized) {
			locationManager.removeUpdates(locationListener)
		}
	}
	
	companion object {
		private const val LOCATION_PERMISSION_REQUEST_CODE = 1
	}
}


//用法
/*

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



* */

