package com.example.healthanalyzers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.healthanalyzers.data.UserInformation
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_report,
                R.id.navigation_knowledge,
                R.id.navigation_mine
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val userInformation = getApplication() as UserInformation
        intent.getStringExtra("account")?.let {
            Log.d("MainActivity", "获取的登录账号为：$it")
//            val fragment = fragmentManager
//            val fragmentTransaction = fragment.beginTransaction()
//            val fragmentMineFragment = MineFragment()
//            val bundle = Bundle()
//            bundle.putString("account", it)
//            fragmentMineFragment.arguments = bundle
//            fragmentTransaction.commit()

            userInformation.account = it
        }
        intent.getStringExtra("password")?.let {
            userInformation.password = it
        }
    }


}