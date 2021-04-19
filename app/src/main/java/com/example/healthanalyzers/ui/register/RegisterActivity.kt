package com.example.healthanalyzers.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.healthanalyzers.R
import com.gyf.immersionbar.ktx.immersionBar

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun onResume() {
        super.onResume()
        immersionBar {
            statusBarColor(R.color.actionbar_color)
        }
    }
}