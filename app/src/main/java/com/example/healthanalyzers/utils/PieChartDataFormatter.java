package com.example.healthanalyzers.utils;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class PieChartDataFormatter extends ValueFormatter {
    private final DecimalFormat myFormatter;
    private PieChart pieChart;

    public PieChartDataFormatter() {
        // 设置数字为整数形式，并且为了阅读，将每三位数字间加上 “,”
        this.myFormatter = new DecimalFormat("###,###,##0");
    }

    public PieChartDataFormatter(PieChart pieChart) {
        this();
        this.pieChart = pieChart;
    }

    @Override
    public String getFormattedValue(float value) {
        return myFormatter.format(value);
    }
}
