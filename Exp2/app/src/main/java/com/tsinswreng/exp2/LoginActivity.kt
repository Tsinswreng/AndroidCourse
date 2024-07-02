package com.tsinswreng.exp2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.tsinswreng.exp2.models.User
import com.tsinswreng.exp2.tswg.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class LoginActivity : AppCompatActivity() {
	
	private lateinit var position: Position
	protected lateinit var alertDialogMk: AlertDialog.Builder
	protected var client = Client.getInstance()
	private lateinit var job: Job
	
	val coroutineContext: CoroutineContext
		get() = Dispatchers.Main + job
	
	protected fun init(){
		this.alertDialogMk = AlertDialog.Builder(this)
		this.job = Job()
	}
	/** 简易封装的弹窗 */
	fun alert(title:String, msg:String){
		val builder = this.alertDialogMk
		builder.setTitle(title)
		builder.setMessage(msg)
		// 设置正面按钮及其点击事件
		builder.setPositiveButton("确定") { dialog, which ->
			// 点击确定按钮后的操作
		}
//		// 设置负面按钮及其点击事件
//		builder.setNegativeButton("取消") { dialog, which ->
//			// 点击取消按钮后的操作
//		}
		builder.create().show()
		
	}
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		this.init()
		setContentView(R.layout.activity_login)

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
		buttonLogin.setOnClickListener  {
			val username = editTextUsername.text.toString()
			val password = editTextPassword.text.toString()
			login(username, password)
		}
		
	}
	
	fun login(userName:String, pswd:String){
		if(userName == "" || pswd == ""){
			alert("错误", "用户名或密码为空")
			return
		}
		
		GlobalScope.launch {
			try {
				val success = withContext(Dispatchers.IO) {
					Client.getInstance().login(userName, pswd)
				}
				runOnUiThread{
					gotoBookList() // 页面跳转
				}
//				if (success) {
//
//				} else {
//					runOnUiThread{
//						alert("错误", "登录失败, 请检查用户名与密码是否匹配")
//					}
//				}
			} catch (err: Exception) {
				println("zzzz")
				System.err.println(err)
				runOnUiThread{
					alert("错误", "登录失败, 请检查用户名与密码是否匹配")
				}
				
			}
		}
//		try {
//			client.login(userName, pswd) // Suspend function 'login' should be called only from a coroutine or another suspend function
//			gotoBookList() //页面跳转
//		}catch (err:Exception){
//			alert("错误", "登录失败, 请检查用户名与密码是否匹配")
//		}
		
	}
	
	fun gotoBookList(){
		val booksIntent = Intent(this, BooksActivity::class.java).apply {
			putExtra("USERNAME", User.currentUser.userName)
		}
		startActivity(booksIntent)
	}
	
	override fun onDestroy() {
		super.onDestroy()
		job.cancel()
	}
	
}
