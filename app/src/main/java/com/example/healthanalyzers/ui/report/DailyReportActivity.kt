package com.example.healthanalyzers.ui.report

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.ReportAdapter
import com.example.healthanalyzers.bean.CustomLineData
import com.example.healthanalyzers.data.UserInformation
import com.example.healthanalyzers.utils.DBUtils
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread

class DailyReportActivity : AppCompatActivity() {

    private var chartDataList: ArrayList<CustomLineData> = ArrayList()
    private lateinit var userName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report)
        supportActionBar?.hide()

        val recyclerView_daily = findViewById<RecyclerView>(R.id.recyclerView_daily)

        userName = (application as UserInformation).account
        initListData()
        val layoutManager = LinearLayoutManager(this)
        recyclerView_daily.layoutManager = layoutManager
        val adapter = ReportAdapter(this, chartDataList, userName)
        recyclerView_daily.adapter = adapter

        // 返回上一页
        val back = findViewById<ImageButton>(R.id.image_btn_back)
        back.setOnClickListener {
            finish()
        }
    }

    private fun initListData() {
        // 查询数据库检测的所有数据
        // TODO


        val time = Calendar.getInstance()
        val date =
            "${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH) + 1}-${time.get(Calendar.DAY_OF_MONTH)}"

        val list1 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // list1.add(Entry(1f, 10f))
        // list1.add(Entry(2f, 20f))
        // list1.add(Entry(3f, 10f))
        // list1.add(Entry(4f, 50f))
        // for ()
        addData(list1, "heartrate", "heartRate", date, userName)
        chartDataList.add(CustomLineData(list1, "心率"))

        val list2 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        addData(list2, "sleepquality", "quality", date, userName)
        chartDataList.add(CustomLineData(list2, "睡眠"))

        val list3 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        addData(list3, "high", "high", date, userName)
        chartDataList.add(CustomLineData(list3, "身高"))

        val list4 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        addData(list4, "bloodoxygen", "ArterialBloodOxygen", date, userName)
        chartDataList.add(CustomLineData(list4, "血氧饱和度"))

        val list5 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        chartDataList.add(CustomLineData(list5, "血压"))

        val list6 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        chartDataList.add(CustomLineData(list6, "血糖"))

        val list7 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        chartDataList.add(CustomLineData(list7, "体重"))

        val list8 = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        chartDataList.add(CustomLineData(list8, "体温"))
    }

    /**
     * list: 代表一个个的坐标
     * table：代表所要查询的内容所在的表
     * mainContent：table 中的最重要的内容（除开用户名、时间），例如心率表中就是心率
     */
    private fun addData(
        list: ArrayList<Entry>,
        table: String,
        mainContent: String,
        date: String,
        account: String
    ) {

        Log.d("DailyReportActivity", date)
        val sql =
            "SELECT HOUR(time), MINUTE(time), $mainContent FROM $table WHERE DATE(time) = '$date' AND userName = $account;"
        var timeOnAxis: Float
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                timeOnAxis = resultSet.getInt(1) + resultSet.getInt(2) / 60.0F
                list.add(BarEntry(timeOnAxis, resultSet.getInt(3).toFloat()))
            }
            DBUtils.close(connection, statement, resultSet)
        }
    }
}