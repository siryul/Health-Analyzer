package com.example.healthanalyzers.ui.mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.data.UserInformation
import com.example.healthanalyzers.databinding.ActivityAlterPasswordBinding
import com.example.healthanalyzers.utils.DBUtils
import es.dmoral.toasty.Toasty
import kotlin.concurrent.thread

class AlterPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlterPasswordBinding

    // 记录返回之前修改结果
    private var alterResult: String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlterPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val userInformation = getApplication() as UserInformation
        initOldPassword(userInformation)


        // 返回上一页
        // val image_btn_back = findViewById<ImageButton>(R.id.image_btn_back)
        binding.imageBtnBack.setOnClickListener {
            // 向数据库提交结果
            // 若是未对密码进行修改，直接返回上一页，无需对数据库更新
            if (!userInformation.password.equals(alterResult)) {
                userInformation.password = alterResult
                commitResult(userInformation)
            }
            finish()
        }

        // 修改密码
        binding.btnAlterPassword.setOnClickListener {
            if (binding.etAlterPasswordFirst.text.toString()
                    .equals("") || binding.etAlterPasswordSecond.text.toString().equals("")
            ) {
                // 任意一个输入框为空
                Toasty.error(this, "输入不能为空！", Toasty.LENGTH_SHORT, true).show()
            } else {
                // 连续两次输入密码相同
                if (binding.etAlterPasswordFirst.text.toString()
                        .equals(binding.etAlterPasswordSecond.text.toString())
                ) {
                    // 判读是否和旧密码相同，相同时，提示不能和之前密码相同，反之提示成功
                    if (binding.etAlterPasswordSecond.text.toString().equals(alterResult)) {
                        Toasty.warning(this, "新密码不能与之前密码相同！", Toasty.LENGTH_SHORT, true).show()
                    } else {
                        alterResult = binding.etAlterPasswordSecond.text.toString()
                        Toasty.success(this, "修改成功！", Toasty.LENGTH_SHORT, true).show()
                    }

                } else {
                    Toasty.error(this, "两次输入不一致！", Toasty.LENGTH_SHORT, true).show()
                }
            }
        }
    }


    private  fun initOldPassword(userInformation: UserInformation) {
        alterResult = userInformation.password
    }

    private fun commitResult(userInformation: UserInformation) {
        // 点击返回上一页时将最终结果提交至数据库
        val sql = "UPDATE user SET password = \'$alterResult\' WHERE userName = \'${userInformation.account}\'"
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            DBUtils.close(connection, statement)
        }
    }
}