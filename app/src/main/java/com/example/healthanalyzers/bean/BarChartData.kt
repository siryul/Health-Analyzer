package com.example.healthanalyzers.bean

import com.github.mikephil.charting.data.BarEntry

// type 表示数据属于哪一类
class BarChartData(val dataList: ArrayList<BarEntry>, val type: String)