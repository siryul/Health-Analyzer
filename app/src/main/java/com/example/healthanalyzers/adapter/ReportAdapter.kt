package com.example.healthanalyzers.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthanalyzers.R
import com.example.healthanalyzers.bean.BarChartData
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet

class ReportAdapter(val context: Context, val barChartDataList: ArrayList<BarChartData>) :
    RecyclerView.Adapter<ReportAdapter.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_barChat_title = view.findViewById<TextView>(R.id.tv_barChat_title)
        val barChart = view.findViewById<BarChart>(R.id.barChart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.report_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barChartList = barChartDataList[position]
        // 设置标题
        holder.tv_barChat_title.text = barChartList.type

        // 设置图标内容
        val dataset = BarDataSet(barChartList.dataList, "")
        val data = BarData(dataset)
        holder.barChart.data = data
    }

    override fun getItemCount() = barChartDataList.size ?: 0
}