package com.example.healthanalyzers.ui.forgotPassword

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.healthanalyzers.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val username = findViewById<EditText>(R.id.username)
        val retrieve = findViewById<Button>(R.id.retrieve)
        retrieve.setOnClickListener {
            // 查找数据库中是否存在该用户
            val sql = "SELECT password FROM user WHERE userID = " + username.text.toString()
            if (true) {
                // 用户存在
                AlertDialog.Builder(this).apply {
                    setTitle("查询结果")
                    setMessage("查询结果密码展示！")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which -> }
                    show()
                }
            } else {
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show()
            }
        }
    }
}