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

    // 初始化数据，尚未完成对具有两种数据的图表合并
    private fun initListData() {
        // 查询数据库检测的所有数据
        val time = Calendar.getInstance()
        val date =
            "${time.get(Calendar.YEAR)}-${time.get(Calendar.MONTH) + 1}-${time.get(Calendar.DAY_OF_MONTH)}"

        val listOfheartRate = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        addData(listOfheartRate, "heartrate", "heartRate", date, userName)
        chartDataList.add(CustomLineData(listOfheartRate, "心率", 60F, 100F, date, 0F, 120F))

        // val listOfsleepQuality = ArrayList<Entry>()
        // // 根据查询的 今日 检测次数进行对 list 扩充
        // addData(listOfsleepQuality, "sleepquality", "quality", date, userName)
        // chartDataList.add(CustomLineData(listOfsleepQuality, "睡眠", date))

        // val listOfHigh = ArrayList<Entry>()
        // // 根据查询的 今日 检测次数进行对 list 扩充
        // addData(listOfHigh, "high", "high", date, userName)
        // chartDataList.add(CustomLineData(listOfHigh, "身高", 30F, 300F, date))

        // val listOfBOA = ArrayList<Entry>()  // 动脉血氧量
        // val listOfBOS = ArrayList<Entry>()  // 静脉血氧量
        // // 根据查询的 今日 检测次数进行对 list 扩充
        // addData(
        //     listOfBOA,
        //     listOfBOS,
        //     "bloodoxygen",
        //     "ArterialBloodOxygen, VenousBloodOxygen",
        //     date,
        //     userName
        // )
        // chartDataList.add(CustomLineData(listOfBOA, "血氧饱和度", date))
        // chartDataList.add(CustomLineData(listOfBOS, "血氧饱和度", date))
//
        // val listOfBPS = ArrayList<Entry>()  // 收缩压
        // val listOfBPD = ArrayList<Entry>()  // 舒张压
        // // 根据查询的 今日 检测次数进行对 list 扩充
        // addData(
        //     listOfBPS,
        //     listOfBPD,
        //     "bloodpressure",
        //     "systolicPressure, diastolicPressure",
        //     date,
        //     userName
        // )
        // chartDataList.add(CustomLineData(listOfBPS, "血压", date))
        // chartDataList.add(CustomLineData(listOfBPD, "血压", date))

        val listOfBS = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        addData(listOfBS, "bloodsugar", "bloodSugar", date, userName)
        chartDataList.add(CustomLineData(listOfBS, "血糖", 70F, 100F, date, 10F, 150F))

        // val listOfWeight = ArrayList<Entry>()
        // // 根据查询的 今日 检测次数进行对 list 扩充
        // addData(listOfWeight, "weight", "weight", date, userName)
        // chartDataList.add(CustomLineData(listOfWeight, "体重", date))

        val listOfTemperature = ArrayList<Entry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        addData(listOfTemperature, "bodytemperature", "temperature", date, userName)
        chartDataList.add(CustomLineData(listOfTemperature, "体温", 36.3F, 37.5F, date, 33F, 43F))
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

    private fun addData(
        list1: ArrayList<Entry>,
        list2: ArrayList<Entry>,
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
                list1.add(BarEntry(timeOnAxis, resultSet.getInt(3).toFloat()))
                list1.add(BarEntry(timeOnAxis, resultSet.getInt(4).toFloat()))
            }
            DBUtils.close(connection, statement, resultSet)
        }
    }
}