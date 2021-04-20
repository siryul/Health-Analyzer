package com.example.healthanalyzers.ui.mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.R

class AlterAgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter_age)
        supportActionBar?.hide()
    }
}