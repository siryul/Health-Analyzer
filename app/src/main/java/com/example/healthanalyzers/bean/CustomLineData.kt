package com.example.healthanalyzers.bean

import com.github.mikephil.charting.data.Entry

/**
 * list 为某项测量结果的数据
 * type 为测量数据为健康数据的类型
 * low 为该项测试的偏低值的界限
 * high 为该项测试的偏高值的界限
 * date 为展示的数据为那一天的
 * start 代表纵坐标的起始点
 * end 代表纵坐标的终点
 */

class CustomLineData(
    val list: ArrayList<Entry>,
    val type: String,
    val low: Float,
    val high: Float,
    var date: String,
    val start: Float,
    val end: Float
)