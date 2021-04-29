package com.example.healthanalyzers.ui.devices

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.DevicesAdapter
import com.example.healthanalyzers.bean.Devices
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ManageDevicesActivity : AppCompatActivity() {

    // 设备 id 计数，避免 id 重复
    private var count  = 0

    // 设备列表
    private var devicesList: ArrayList<Devices> = ArrayList<Devices>()

    // 待添加设备
    private var waitToAddDevicesList: ArrayList<Devices> = ArrayList<Devices>()

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
            // 返回上一页之前将待添加数据 waitToAddDevicesList 加入数据库

            finish()
        }

        // 点击添加按钮，添加设备
        val add_device = findViewById<FloatingActionButton>(R.id.add_device)
        add_device.setOnClickListener {
            val popupWindow_layout =
                LayoutInflater.from(this).inflate(R.layout.popupwindow_add_devices, null)
            val popupWindow = PopupWindow(
                popupWindow_layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
            )


            val location = IntArray(2)
            it.getLocationOnScreen(location)
            popupWindow.showAtLocation(
                it,
                Gravity.TOP and Gravity.LEFT,
                location[0]-popupWindow.width-10,
                location[1]-popupWindow.height-30
            )
            popupWindow.isTouchable = true


            // 处理列表中事件
            val tv_blood_glucose_meter =
                popupWindow_layout.findViewById<TextView>(R.id.tv_blood_glucose_meter)
            tv_blood_glucose_meter.setOnClickListener {
                // 添加血糖仪
                // waitToAddDevicesList.add(Devices(count++, ))
            }

            val tv_body_fat_scale =
                popupWindow_layout.findViewById<TextView>(R.id.tv_body_fat_scale)
            tv_body_fat_scale.setOnClickListener {
                // 添加体脂秤
                // waitToAddDevicesList.add(Devices())
            }

            val tv_heart_rate_device =
                popupWindow_layout.findViewById<TextView>(R.id.tv_heart_rate_device)
            tv_heart_rate_device.setOnClickListener {
                // 添加心率仪
                // waitToAddDevicesList.add(Devices())
            }

            val tv_height_meter = popupWindow_layout.findViewById<TextView>(R.id.tv_height_meter)
            tv_height_meter.setOnClickListener {
                // 添加身高测定仪
                // waitToAddDevicesList.add(Devices())
            }

            val tv_oximeter = popupWindow_layout.findViewById<TextView>(R.id.tv_oximeter)
            tv_oximeter.setOnClickListener {
                // 添加血氧仪
                // waitToAddDevicesList.add(Devices())
            }

            val tv_sphygmomanometer =
                popupWindow_layout.findViewById<TextView>(R.id.tv_sphygmomanometer)
            tv_sphygmomanometer.setOnClickListener {
                // 添加血压计
                // waitToAddDevicesList.add(Devices())
            }

            val tv_thermometer = popupWindow_layout.findViewById<TextView>(R.id.tv_thermometer)
            tv_thermometer.setOnClickListener {
                // 添加温度计
                // waitToAddDevicesList.add(Devices())
            }

            val tv_wristband_device =
                popupWindow_layout.findViewById<TextView>(R.id.tv_wristband_device)
            tv_wristband_device.setOnClickListener {
                // 添加手环
                // waitToAddDevicesList.add(Devices())
            }
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

        count = devicesList.size
    }

    override fun onResume() {
        super.onResume()

    }
}