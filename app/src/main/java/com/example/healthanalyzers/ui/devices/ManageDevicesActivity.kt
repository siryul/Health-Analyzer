package com.example.healthanalyzers.ui.devices

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.DevicesAdapter
import com.example.healthanalyzers.bean.Devices
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManageDevicesActivity : AppCompatActivity() {
    // 设备列表
    private var devicesList: ArrayList<Devices> = ArrayList<Devices>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)
        supportActionBar?.hide()

        // 初始化已拥有的设备
        initDevicesList()

        // 设置设备列表 recyclerView
        val layoutManager = GridLayoutManager(this, 2)
        val recyclerView_device = findViewById<RecyclerView>(R.id.recyclerView_device)
        recyclerView_device.layoutManager = layoutManager
        val adapter = DevicesAdapter(this, devicesList)
        recyclerView_device.adapter = adapter

        // 返回上一页
        val image_btn_back = findViewById<ImageButton>(R.id.image_btn_back)
        image_btn_back.setOnClickListener {
            finish()
        }

        // 点击添加按钮，添加设备
        val add_device = findViewById<FloatingActionButton>(R.id.add_device)
        add_device.setOnClickListener {

        }
    }

    // type 有效值与对应关系：
//  1 ————> 心率仪
//  2 ————> 手环/表
//  3 ————> 身高测定仪
//  4 ————> 血氧测定仪
//  5 ————> 血压计
//  6 ————> 血糖仪
//  7 ————> 称
//  8 ————> 体温计
    fun initDevicesList() {
        // 从数据库中查询已拥有的设备


        // 测试
        devicesList.add(Devices(1, "体脂秤", R.drawable.weigh, 7))
        devicesList.add(Devices(2, "体温计", R.drawable.thermommeter, 8))
    }

    override fun onResume() {
        super.onResume()

    }
}