package com.example.healthanalyzers.ui.mine

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.healthanalyzers.R
import com.example.healthanalyzers.databinding.ActivityAlterUsernameBinding
import com.example.healthanalyzers.ui.mine.AlterUsernameActivity.Companion.actionStart
import com.gyf.immersionbar.ktx.immersionBar

class AlterUsernameActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context, data:String) {
            val intent = Intent(context, MineFragment::class.java)
            intent.putExtra("newUsername", data)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_username)
        supportActionBar?.hide()
        val editText_username = findViewById<EditText>(R.id.et_alter_username)
        val back = findViewById<ImageButton>(R.id.image_btn_back)
        back.setOnClickListener {
            // MineFragment.actionStart(this, editText_username.text)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        immersionBar{
            statusBarColor(R.color.MainActivityColor)
            //fitsSystemWindows(true)
        }
    }
}