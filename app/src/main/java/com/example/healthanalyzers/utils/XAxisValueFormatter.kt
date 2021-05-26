package com.example.healthanalyzers.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class XAxisValueFormatter(private val x: ArrayList<String>) : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val valueToInt = value.toInt()
        return if (valueToInt < x.size) {
            x[valueToInt]
        } else {
            ""
        }
    }
}