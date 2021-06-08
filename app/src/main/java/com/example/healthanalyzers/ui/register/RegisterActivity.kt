package com.example.healthanalyzers.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.databinding.ActivityRegisterBinding
import com.example.healthanalyzers.ui.login.LoginActivity
import com.example.healthanalyzers.utils.DBUtils
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // 点击注册
        binding.register.setOnClickListener {
            if (isInputRight()) {
                register()
            }
        }

        // 返回登录界面
        binding.imageBtnBackRegister.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun isInputRight(): Boolean {
        if (binding.username.text.toString().isEmpty() || binding.password.text.toString()
                .isEmpty() || binding.passwordInputAgain.text.toString()
                .isEmpty() || binding.nickname.text.toString().isEmpty()
        ) {
            Toasty.error(this, "内容未填写完整", Toasty.LENGTH_SHORT).show()
            return false
        }
        if (binding.password.text.toString() != binding.passwordInputAgain.text.toString()) {
            Toasty.error(this, "两次密码输入不一致", Toasty.LENGTH_SHORT).show()
            return false
        }
        if (binding.password.text.toString().length < 6) {
            Toasty.error(this, "密码长度至少6位", Toasty.LENGTH_SHORT).show()
        }
        return true
    }

    private fun register() {
        var sql = "SELECT userName FROM `user` WHERE userName = ${binding.username.text}"
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            // 首先查询该账号是否存在
            val resultSet = statement.executeQuery(sql)
            Looper.prepare()
            if (resultSet.next()) {
                Toasty.warning(this, "用户名已存在，换一个试试", Toasty.LENGTH_SHORT).show()
            } else {
                sql =
                    "INSERT INTO `user` (userName, nickName, `password`) VALUES (${binding.username.text}, \"${binding.nickname.text}\", ${binding.password.text})"
                if (statement.executeUpdate(sql) != 0) {
                    Toasty.success(this, "注册成功", Toasty.LENGTH_SHORT).show()
                }
            }
            DBUtils.close(connection, statement, resultSet)
            Looper.loop()
        }
    }
}