package com.example.healthanalyzers.ui.devices

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.DevicesAdapter
import com.example.healthanalyzers.bean.Devices
import es.dmoral.toasty.Toasty

class ManageDevicesActivity : AppCompatActivity() {

    // 设备 id 计数，避免 id 重复
    private var count = 0

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
    } // onCreate


    //      type 有效值与对应关系：
//      1 ————> 心率仪
//      2 ————> 手环/表
//      3 ————> 身高测定仪
//      4 ————> 血氧测定仪
//      5 ————> 血压计
//      6 ————> 血糖仪
//      7 ————> 称
//      8 ————> 体温计
    fun initDevicesList() {


// 从数据库中查询已拥有的设备

// 测试
        devicesList.add(Devices(++count, "体脂秤", R.drawable.weigh, 7))
        devicesList.add(Devices(++count, "体温计", R.drawable.thermommeter, 8))

        Log.d("ManageDevicesActivity", "" + (count == devicesList.size))
    }

    // 添加体温计
    fun addThermometer(view: View) {
        Toasty.success(this, "you clicked thermometer", Toasty.LENGTH_SHORT).show()
        devicesList.add(Devices(++count, "体温计", R.drawable.ic_thermometer_24dp, 8))
    }

    // 添加手表/手环
    fun addWatch(view: View) {
        devicesList.add(Devices(++count, "手表", R.drawable.ic_watch_24dp, 2))
    }

    // 添加心率仪
    fun addHeartRateDevice(view: View) {}

    // 添加身高测定仪
    fun addHeightMeter(view: View) {}

    // 添加血压计
    fun addSphygmomanometer(view: View) {}

    // 添加血氧仪
    fun addOximeter(view: View) {}

    // 添加血糖仪
    fun addBloodGlucoseMeter(view: View) {}

    // 添加体脂称
    fun addBodyFatScale(view: View) {}


}