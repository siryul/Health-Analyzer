package com.example.healthanalyzers.ui.devices

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.DevicesAdapter
import com.example.healthanalyzers.bean.Devices
import com.example.healthanalyzers.data.UserInformation
import com.example.healthanalyzers.utils.DBUtils
import com.example.healthanalyzers.utils.MyItemTouchCallback
import kotlin.concurrent.thread

class ManageDevicesActivity : AppCompatActivity() {

    private var total = 0                                               // 拥有的设备总数
    private var count = 0                                               // 设备 id 计数，避免 id 重复
    private var userName: Int? = null                                   // 用户账号
    private var devicesList: ArrayList<Devices> = ArrayList()           // 设备列表
    private var waitToAddDevicesList: ArrayList<Devices> = ArrayList()  // 待添加设备
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_devices)
        supportActionBar?.hide()
        val userInformation = this.application as UserInformation
        userName = userInformation.account.toInt()
        // 初始化已拥有的设备
        initDevicesList()

        // 设置设备列表 recyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView_device)
        recyclerView.layoutManager = layoutManager
        val adapter = DevicesAdapter(this, devicesList, userName!!.toInt())
        recyclerView.adapter = adapter
        // 设置 recyclerView 的滑动事件的监听，此处可以传入 adapter 是由于自定义的 DevicesAdapter 实现了对应的接口
        val itemTouchCallback = MyItemTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // 返回上一页
        val back: ImageButton = findViewById(R.id.image_btn_back)
        back.setOnClickListener {
            finish()
        }
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
    // 初始化设备列表
    private fun initDevicesList() {
        // 从数据库中查询已拥有的设备
        thread {
            var sql = "SELECT id, `name`, type FROM `devices` WHERE userName = $userName"
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            var resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                ++total
                when (resultSet.getInt("type")) {
                    1 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_heart_rate_color_32dp,
                                1
                            )
                        )
                    }
                    2 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_watch_color_24dp,
                                2
                            )
                        )
                    }
                    3 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_high_color_24dp,
                                3
                            )
                        )
                    }
                    4 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_blood_oxygen_color_24dp,
                                4
                            )
                        )
                    }
                    5 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_blood_pressure_color_24dp,
                                5
                            )
                        )
                    }
                    6 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_blood_suger_color_24dp,
                                6
                            )
                        )
                    }
                    7 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_weight_color_24dp,
                                7
                            )
                        )
                    }
                    8 -> {
                        devicesList.add(
                            Devices(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                R.drawable.ic_thermometer_color_24dp,
                                8
                            )
                        )
                    }
                }
            }
            // 找出设备的最大序号
            sql = "SELECT MAX(id) FROM `devices`"
            resultSet = statement.executeQuery(sql)
            if (resultSet.next()) {
                // 保证设备序号不会再次从头开始，只会从已有设备列表的序号最大值开始
                count = if (count < resultSet.getInt(1)) resultSet.getInt(1) else count
            }

            DBUtils.close(connection, statement, resultSet)
        }
        // 测试
        // devicesList.add(Devices(++count, "体脂秤", R.drawable.ic_weight_color_24dp, 7))
        // devicesList.add(Devices(++count, "体温计", R.drawable.ic_thermometer_color_24dp, 8))
        // Log.d("ManageDevicesActivity", "" + (count == devicesList.size))
    }

    // 添加心率仪
    fun addHeartRateDevice(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"心率仪\", 1, $userName)"
        devicesList.add(Devices(count, "心率仪", R.drawable.ic_heart_rate_color_32dp, 1))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加手表/手环
    fun addWatch(view: View) {
        devicesList.add(Devices(++count, "手表", R.drawable.ic_watch_color_24dp, 2))
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES($count, \"手表\", 2, $userName)"
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加身高测定仪
    fun addHeightMeter(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"身高测定仪\", 3, $userName)"
        devicesList.add(Devices(count, "身高测定仪", R.drawable.ic_high_color_24dp, 3))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加血氧仪
    fun addOximeter(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"血氧仪\", 4, $userName)"
        devicesList.add(Devices(count, "血氧仪", R.drawable.ic_blood_oxygen_color_24dp, 4))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加血压计
    fun addSphygmomanometer(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"血压计\", 5, $userName)"
        devicesList.add(Devices(count, "血压计", R.drawable.ic_blood_pressure_color_24dp, 5))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加血糖仪
    fun addBloodGlucoseMeter(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"血糖仪\", 6, $userName)"
        devicesList.add(Devices(count, "血糖仪", R.drawable.ic_blood_suger_color_24dp, 6))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加体脂称
    fun addBodyFatScale(view: View) {
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"体脂秤\", 7, $userName)"
        devicesList.add(Devices(count, "体脂秤", R.drawable.ic_weight_color_24dp, 7))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    // 添加体温计
    fun addThermometer(view: View) {
        // Toasty.success(this, "you clicked thermometer", Toasty.LENGTH_SHORT).show()
        val sql =
            "INSERT INTO devices (id, `name`, type, userName) VALUES(${++count}, \"体温计\", 8, $userName)"
        devicesList.add(Devices(++count, "体温计", R.drawable.ic_thermometer_color_24dp, 8))
        addDevice(sql)
        recyclerView.adapter?.notifyItemInserted(total)
    }

    private fun addDevice(sql: String) {
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            statement.executeUpdate(sql)
            DBUtils.close(connection, statement)
        }
    }
}