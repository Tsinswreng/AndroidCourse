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
import kotlin.coroutines.CoroutineContext

class RegisterActivity : AppCompatActivity() {
    
    var userName:String = ""
    var pswd:String = ""
    var confirmPswd:String = ""
    
    
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
        setContentView(R.layout.activity_register)

        // 获取控件引用
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        // 注册按钮点击事件
        buttonRegister.setOnClickListener {
            // 获取用户名、密码和确认密码
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            this.userName = username
            this.pswd = password
            this.confirmPswd = confirmPassword
            this.signUp()
        }
    }
    
    fun signUp(){
        val z = this
        if(userName == "" || pswd == ""){
            z.alert("错误", "用户名或密码为空")
            return
        }
        if(z.pswd != z.confirmPswd){
            z.alert("错误", "两次输入的密码不一致")
            return
        }
        GlobalScope.launch {
            try {
                z.client.signUp(z.userName, z.pswd)
                runOnUiThread{
                    val dl = z.alertDialogMk
                    dl.setTitle("")
                    dl.setMessage("注册成功")
                    dl.setPositiveButton("登录") { dialog, which ->
                        //z.gotoBookList()
                        z.finish()
                    }
                    dl.show()
                }
            }catch (e:Exception){
                System.err.println(e)
                runOnUiThread{
                    z.alert("错误", "注册失败。用户名可能已被占用")
                }
            }
            
        }
        
    }
    
    fun gotoBookList(){
        val booksIntent = Intent(this, BooksActivity::class.java).apply {
            putExtra("USERNAME", User.currentUser.userName)
        }
        startActivity(booksIntent)
    }
}