package com.example.healthanalyzers

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.ui.login.LoginActivity

class LaunchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}