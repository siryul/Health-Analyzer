package com.example.healthanalyzers.ui.mine

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.R
import es.dmoral.toasty.Toasty

class AlterUsernameActivity : AppCompatActivity() {
    // 记录点击过保存的用户名最新结果
    private var resultUsername: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_username)
        supportActionBar?.hide()

        val editText_username = findViewById<EditText>(R.id.et_alter_username)
        val back = findViewById<ImageButton>(R.id.image_btn_back)
        val btn_alter_username = findViewById<Button>(R.id.btn_alter_username)
        back.setOnClickListener {
            // 将最新结果返回数据库

            finish()
        }
        btn_alter_username.setOnClickListener {
            if (!editText_username.text.toString().equals("")){
                // 查询数据库该用户名是否已经存在，若存在，提示修改失败，防止提示成功
                if (true) {
                    // 可以修改为该用户名
                    Toasty.success(this, "修改成功", Toast.LENGTH_SHORT, true).show()
                    resultUsername = editText_username.text.toString()
                } else {
                    Toasty.warning(this, "该用户名已存在，换一个试试", Toast.LENGTH_SHORT, true).show()
                }
            } else {
                Toasty.error(this, "用户名不能为空", Toasty.LENGTH_SHORT, true).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        immersionBar{
//            statusBarColor(R.color.MainActivityColor)
//            //fitsSystemWindows(true)
//        }
    }
}