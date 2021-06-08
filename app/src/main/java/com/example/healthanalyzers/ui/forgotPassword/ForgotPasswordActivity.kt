package com.example.healthanalyzers.ui.forgotPassword

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.R
import com.example.healthanalyzers.ui.login.LoginActivity
import com.example.healthanalyzers.utils.DBUtils
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        supportActionBar?.hide()

        val username = findViewById<EditText>(R.id.username)
        val retrieve = findViewById<Button>(R.id.retrieve)
        retrieve.setOnClickListener {
            // 查找数据库中是否存在该用户
            val sql = "SELECT password FROM user WHERE userName = " + username.text.toString()
            queryPassword(sql)
        }

        // 返回上一页
        val imageButtonBack: ImageButton = findViewById(R.id.image_btn_back_forgot_password)
        imageButtonBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun queryPassword(sql: String) {
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            Looper.prepare()
            if (resultSet.next()) {
                // 用户存在
                AlertDialog.Builder(this).apply {
                    setTitle("查询结果")
                    setMessage("密码为：${resultSet.getString(1)}")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which -> }
                    show()
                }
            } else {
                Toasty.error(this, "用户不存在！", Toasty.LENGTH_SHORT).show()
            }
            Looper.loop()
            DBUtils.close(connection, statement, resultSet)
        }
    }
}