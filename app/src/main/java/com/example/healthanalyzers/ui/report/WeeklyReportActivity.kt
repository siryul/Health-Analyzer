package com.example.healthanalyzers.ui.report

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthanalyzers.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

// 周报

class WeeklyReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekly_report)
        supportActionBar?.hide()

        
        test()
    }

    fun test() {
        // 测试画图表
        val lineChart = findViewById<BarChart>(R.id.lineChart)

        val list = ArrayList<BarEntry>()
        list.add(BarEntry(1F, 1F))
        list.add(BarEntry(2F, 3F))
        list.add(BarEntry(3F, 2F))
        list.add(BarEntry(4F, 8F))
        list.add(BarEntry(5F, 5F))
        val lineDataSet = BarDataSet(list, "Test")
        val barData = BarData(lineDataSet)
        lineChart.data = barData
        val x = lineChart.xAxis
        x.setCenterAxisLabels(true)
        val y = lineChart.rendererLeftYAxis

    }
}