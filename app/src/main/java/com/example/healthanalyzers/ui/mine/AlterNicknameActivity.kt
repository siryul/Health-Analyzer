package com.example.healthanalyzers.ui.mine

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.R
import es.dmoral.toasty.Toasty

class AlterNicknameActivity : AppCompatActivity() {
    // 记录点击过保存的用户名最新结果
    private var resultNickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_nickname)

        val editText_nickname = findViewById<EditText>(R.id.et_alter_nickname)
        val btn_alter_nickname = findViewById<Button>(R.id.btn_alter_nickname)

        btn_alter_nickname.setOnClickListener {
            if (!editText_nickname.text.toString().equals("")) {
                // 昵称可以重复
                Toasty.success(this, "修改成功", Toast.LENGTH_SHORT, true).show()
                resultNickname = editText_nickname.text.toString()
            } else {
                Toasty.warning(this, "昵称不能为空", Toasty.LENGTH_SHORT, true).show()
            }
        }

        val image_btn_back = findViewById<ImageButton>(R.id.image_btn_back)
        image_btn_back.setOnClickListener {
            // 将最后的结果存回数据库

            finish()
        }
        supportActionBar?.hide()
    }
}