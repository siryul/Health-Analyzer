package com.example.healthanalyzers.ui.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.adapter.ReportAdapter
import com.example.healthanalyzers.bean.BarChartData
import com.example.healthanalyzers.utils.DBUtils
import com.github.mikephil.charting.data.BarEntry
import kotlin.concurrent.thread

class DailyReportActivity : AppCompatActivity() {

    private var barChartDataList: ArrayList<BarChartData> = ArrayList<BarChartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report)
        supportActionBar?.hide()

        val recyclerView_daily = findViewById<RecyclerView>(R.id.recyclerView_daily)

        initListData()

        val layoutManager = LinearLayoutManager(this)
        recyclerView_daily.layoutManager = layoutManager
        val adapter = ReportAdapter(this, barChartDataList)
        recyclerView_daily.adapter = adapter
    }

    private fun initListData() {
        // 查询数据库检测的所有数据
        // TODO

        val list1 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list1, "心率"))

        val list2 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list2, "睡眠"))

        val list3 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list3, "身高"))

        val list4 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list4, "血氧饱和度"))

        val list5 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list5, "血压"))

        val list6 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list6, "血糖"))

        val list7 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list7, "体重"))

        val list8 = ArrayList<BarEntry>()
        // 根据查询的 今日 检测次数进行对 list 扩充
        // for ()
        barChartDataList.add(BarChartData(list8, "体温"))
    }

    fun addData(list: ArrayList<BarEntry>, sql: String) {
        thread {
            val connection = DBUtils.getConnection()
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()) {
                // list.add(BarEntry(resultSet.getDate("time")))
            }
        }
    }
}